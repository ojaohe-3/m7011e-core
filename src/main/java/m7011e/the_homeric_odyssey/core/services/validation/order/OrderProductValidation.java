package m7011e.the_homeric_odyssey.core.services.validation.order;

import java.util.Objects;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.core.repository.ProductRepository;
import m7011e.the_homeric_odyssey.coreorm.orm.ProductDb;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.ProductStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class OrderProductValidation implements OrderVerificationRow {

  private final ProductRepository productRepository;

  private static final Set<ProductStatus> INVALID_PRODUCT_STATUES =
      Set.of(ProductStatus.NOT_AVAILABLE, ProductStatus.PENDING);

  @Override
  public void validate(@NonNull Order resource, Errors errors) {
    if (Objects.isNull(resource.getProductId())
        || !productRepository.existsById(resource.getProductId())) {
      errors.rejectValue("product", "order.product.must.exist");
      return;
    }

    rejectOnCondition(
        productRepository
            .findById(resource.getProductId())
            .map(ProductDb::getStatus)
            .filter(INVALID_PRODUCT_STATUES::contains)
            .isPresent(),
        errors,
        "product",
        "order.product.invalid.state");
  }
}
