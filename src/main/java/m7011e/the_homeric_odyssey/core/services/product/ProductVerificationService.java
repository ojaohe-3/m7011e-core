package m7011e.the_homeric_odyssey.core.services.product;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m7011e.the_homeric_odyssey.core.services.validation.product.ProductVerificationRow;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import m7011e.the_homeric_odyssey.resource_server.exceptions.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductVerificationService {

  private final Set<ProductVerificationRow> productVerificationRows;

  public void verifyProduct(final Product product) {
    Errors errors = new BeanPropertyBindingResult(product, "product");

    productVerificationRows.forEach(row -> row.validate(product, errors));

    if (errors.hasErrors()) {
      log.error("Product returned validation Errors");
      throw new ValidationException(errors);
    }
  }
}
