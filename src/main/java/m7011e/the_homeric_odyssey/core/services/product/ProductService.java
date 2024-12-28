package m7011e.the_homeric_odyssey.core.services.product;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.core.configuration.CoreVendorConfigurationProperties;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final CoreVendorConfigurationProperties properties;

  private final ProductPersistenceService productPersistenceService;

  private final ProductVerificationService productVerificationService;

  private final ProductAuthenticationService productAuthenticationService;

  private final ModelMapper modelMapper;

  private final UserAuthenticationHelper userAuthenticationHelper;

  public Product getProduct(UUID id) {
    Product product = productPersistenceService.get(id);
    productAuthenticationService.hasReadPermission(product);
    return product;
  }

  public Product createProduct(ProductCreateCommand command) {
    Product product = modelMapper.map(command, Product.class);
    productVerificationService.verifyProduct(product);
    return productPersistenceService.create(product);
  }

  public Product updateProduct(UUID id, Long version, ProductUpdateCommand command) {
    Product product = getProduct(id);
    productAuthenticationService.hasWritePermission(product);
    modelMapper.map(command, product);
    productVerificationService.verifyProduct(product);
    return productPersistenceService.update(product, id, version);
  }

  public Page<Product> queryProducts(ProductListCommand command) {

    if (CollectionUtils.isEmpty(command.getStatuses())) {
      command.setStatuses(properties.getProductPubliclyAvailable());
    } else {
      command.setSub(UUID.fromString(userAuthenticationHelper.getUserId().orElseThrow()));
    }

    Page<Product> query = productPersistenceService.query(command);
    query.getContent().forEach(productAuthenticationService::hasReadPermission);
    return query;
  }

  public void deleteProduct(UUID id, Long version) {
    productPersistenceService.delete(id, version);
  }
}
