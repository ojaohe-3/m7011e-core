package m7011e.the_homeric_odyssey.core.services.order;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.core.services.integration.EventLogIntegrationService;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderStatusUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.OrderStatus;
import m7011e.the_homeric_odyssey.resource_server.exceptions.ForbiddenException;
import m7011e.thehomericodyssey.eventlogmodels.models.EventType;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

  private final OrderPersistenceService orderPersistenceService;

  private final OrderAuthenticationService authenticationService;

  private final UserAuthenticationHelper userAuthenticationHelper;

  private final OrderVerificationService orderVerificationService;

  private final EventLogIntegrationService eventLogIntegrationService;

  private final ModelMapper modelMapper;

  public Order getOrder(UUID orderId) {
    Order order = orderPersistenceService.get(orderId);
    checkReadPermission(orderId, order);
    return order;
  }

  public Order createOrder(OrderCreateCommand orderCreateCommand) {
    Order order = modelMapper.map(orderCreateCommand, Order.class);
    order.setSub(UUID.fromString(userAuthenticationHelper.getUserId().orElseThrow()));
    orderVerificationService.verifyOrder(order);
    order.setTotalPrice(order.getProduct().getPrice() * order.getQuantity());

    Order createOrder = orderPersistenceService.create(order);
    eventLogIntegrationService.sendOrderEvent(order, EventType.CREATED, "Order created");
    return createOrder;
  }

  public Order updateOrderStatus(OrderStatusUpdateCommand command, UUID orderId, Long version) {
    Order order = orderPersistenceService.get(orderId);
    checkWritePermission(order.getId(), order);

    order.setStatus(command.newStatus());
    orderVerificationService.verifyOrder(order);

    Order updateOrder = orderPersistenceService.update(order, orderId, version);
    eventLogIntegrationService.sendOrderEvent(
        updateOrder, EventType.MODIFIED, "Order status changed");
    return updateOrder;
  }

  public Order updateOrder(OrderCreateCommand command, UUID orderId, Long version) {
    Order order = orderPersistenceService.get(orderId);
    checkWritePermission(order.getId(), order);

    modelMapper.map(command, order);
    orderVerificationService.verifyOrder(order);

    Order updateOrder = orderPersistenceService.update(order, orderId, version);
    eventLogIntegrationService.sendOrderEvent(
        updateOrder, EventType.UPDATED, "Order has been updated");
    return updateOrder;
  }

  private void checkReadPermission(UUID orderId, Order order) {
    if (!authenticationService.hasReadPermission(order)) {
      log.error(
          "User {} does not have read access to Order {}",
          userAuthenticationHelper.getUserId().orElse(null),
          orderId);
      throw new ForbiddenException("User does not have read access to Order.");
    }
  }

  private void checkWritePermission(UUID orderId, Order order) {
    if (!authenticationService.hasWritePermission(order)) {
      log.error(
          "User {} does not have write access to Order {}",
          userAuthenticationHelper.getUserId().orElse(null),
          orderId);
      throw new ForbiddenException("User does not have write access to Order.");
    }
  }

  public Page<Order> queryOrders(OrderListCommand command) {
    command.setSub(UUID.fromString(userAuthenticationHelper.getUserId().orElseThrow()));
    return orderPersistenceService.query(command);
  }

  public Order cancelOrder(UUID orderId, Long version) {
    Order existingOrder = orderPersistenceService.get(orderId);
    checkWritePermission(existingOrder.getId(), existingOrder);

    existingOrder.setCancelledAt(LocalDateTime.now(ZoneOffset.UTC));
    existingOrder.setStatus(OrderStatus.CANCELLED);
    orderVerificationService.verifyOrder(existingOrder);

    Order updateOrder = orderPersistenceService.update(existingOrder, orderId, version);
    eventLogIntegrationService.sendOrderEvent(
        updateOrder, EventType.CANCELED, "Order cancelled");
    return updateOrder;
  }
}
