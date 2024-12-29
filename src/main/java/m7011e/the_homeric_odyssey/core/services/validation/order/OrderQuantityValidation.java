package m7011e.the_homeric_odyssey.core.services.validation.order;

import java.util.Objects;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class OrderQuantityValidation implements OrderVerificationRow {

  @Override
  public void validate(@NotNull Order order, Errors errors) {
    rejectOnCondition(
         Objects.isNull(order.getQuantity()) || order.getQuantity() < 0,
        errors,
        "quantity",
        "order.invalid.quantity");
  }
}
