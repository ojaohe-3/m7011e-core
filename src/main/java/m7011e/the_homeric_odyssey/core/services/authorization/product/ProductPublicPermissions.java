package m7011e.the_homeric_odyssey.core.services.authorization.product;

import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.core.configuration.CoreVendorConfigurationProperties;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPublicPermissions implements ProductAuthenticationRow {

  private final CoreVendorConfigurationProperties properties;

  @Override
  public boolean hasReadPermission(Product resource) {
    return properties.getProductPubliclyAvailable().contains(resource.getStatus());
  }

  @Override
  public boolean hasWritePermission(Product resource) {
    return false;
  }
}
