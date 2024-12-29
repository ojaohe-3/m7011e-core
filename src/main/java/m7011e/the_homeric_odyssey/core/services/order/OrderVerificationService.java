package m7011e.the_homeric_odyssey.core.services.order;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.core.services.validation.order.OrderVerificationRow;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.resource_server.exceptions.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class OrderVerificationService {

  private final Set<OrderVerificationRow> orderVerificationRows;

  public void verifyOrder(final Order order) {
    Errors errors = new BeanPropertyBindingResult(order, "order");

    orderVerificationRows.forEach(row -> row.validate(order, errors));

    if (errors.hasErrors()) {
      throw new ValidationException(errors);
    }
  }
}
