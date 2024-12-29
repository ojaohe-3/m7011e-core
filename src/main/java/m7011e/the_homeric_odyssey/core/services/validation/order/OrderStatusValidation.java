package m7011e.the_homeric_odyssey.core.services.validation.order;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.core.repository.OrderRepository;
import m7011e.the_homeric_odyssey.core.util.ResourceAuthorizationUtil;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.OrderStatus;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class OrderStatusValidation implements OrderVerificationRow {

  private static final Map<OrderStatus, Set<OrderStatus>> VALID_TRANSITIONS =
      Map.of(
          OrderStatus.PENDING, Set.of(OrderStatus.PAID, OrderStatus.CANCELLED),
          OrderStatus.PAID, Set.of(OrderStatus.PROCESSING, OrderStatus.CANCELLED),
          OrderStatus.PROCESSING, Set.of(OrderStatus.SHIPPED, OrderStatus.CANCELLED),
          OrderStatus.SHIPPED, Set.of(OrderStatus.DELIVERED),
          OrderStatus.DELIVERED, Set.of(), // Terminal state
          OrderStatus.CANCELLED, Set.of() // Terminal state
          );

  private static final Set<String> HANDLER_ALLOWED_FIELDS =
      Set.of("shippedAt", "paymentStatus", "paidAt", "status", "deliveredAt", "cancelledAt");

  private final OrderRepository orderRepository;

  private final ModelMapper modelMapper;

  private final UserAuthenticationHelper userAuthenticationHelper;

  @Override
  public void validate(@NotNull Order resource, Errors errors) {
    if (resource.getStatus() == null) {
      errors.rejectValue("status", "order.status.must.exits", "The order status must not be null");
      return;
    }

    if (Objects.nonNull(resource.getId()) && orderRepository.existsById(resource.getId())) {

      Order existingOrder =
          modelMapper.map(orderRepository.findById(resource.getId()).get(), Order.class);

      if (!ResourceAuthorizationUtil.isOwner(userAuthenticationHelper, resource.getSub())) {
        validateOrderModification(existingOrder, resource, errors);
      }

      rejectOnCondition(
          !isValidStatusTransition(existingOrder.getStatus(), resource.getStatus()),
          errors,
          "status",
          "order.status.transition.invalid");
    }
  }

  private boolean isValidStatusTransition(OrderStatus current, OrderStatus next) {
    return VALID_TRANSITIONS.getOrDefault(current, Set.of()).contains(next);
  }

  private void validateOrderModification(Order existingOrder, Order updatedOrder, Errors errors) {
    Field[] fields = Order.class.getDeclaredFields();

    for (Field field : fields) {
      field.setAccessible(true);
      try {
        Object existingValue = field.get(existingOrder);
        Object updatedValue = field.get(updatedOrder);

        if (!HANDLER_ALLOWED_FIELDS.contains(field.getName())) {
          if (!Objects.equals(existingValue, updatedValue)) {
            errors.rejectValue(
                field.getName(),
                "order.invalid.modification",
                String.format(
                    "Unauthorized modification of field '%s'. Only %s can be modified.",
                    field.getName(), String.join(", ", HANDLER_ALLOWED_FIELDS)));
          }
        }
      } catch (IllegalAccessException e) {
        throw new IllegalStateException("Error accessing field during validation", e);
      }
    }
  }
}
