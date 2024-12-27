package m7011e.the_homeric_odyssey.core.services.authorization.product;

import java.util.Set;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.ProductStatus;
import org.springframework.stereotype.Component;

@Component
public class ProductPublicPermissions implements ProductAuthenticationRow {

  private final Set<ProductStatus> allowedReadableStatues = // TODO make configurable
      Set.of(ProductStatus.AVAILABLE, ProductStatus.PREORDER, ProductStatus.EMPTY);

  @Override
  public boolean hasReadPermission(Product resource) {
    return allowedReadableStatues.contains(resource.getStatus());
  }

  @Override
  public boolean hasWritePermission(Product resource) {
    return false;
  }
}
