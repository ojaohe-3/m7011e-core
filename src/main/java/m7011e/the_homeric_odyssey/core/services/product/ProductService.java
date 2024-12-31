package m7011e.the_homeric_odyssey.core.services.product;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.core.configuration.CoreVendorConfigurationProperties;
import m7011e.the_homeric_odyssey.core.services.integration.EventLogIntegrationService;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import m7011e.thehomericodyssey.eventlogmodels.models.EventType;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

  private final CoreVendorConfigurationProperties properties;

  private final ProductPersistenceService productPersistenceService;

  private final ProductVerificationService productVerificationService;

  private final ProductAuthenticationService productAuthenticationService;

  private final ModelMapper modelMapper;

  private final UserAuthenticationHelper userAuthenticationHelper;

  private final EventLogIntegrationService eventLogIntegrationService;

  public Product getProduct(UUID id) {
    Product product = productPersistenceService.get(id);
    log.debug("Fetched product {}", id);
    productAuthenticationService.hasReadPermission(product);
    return product;
  }

  public Product createProduct(ProductCreateCommand command) {
    Product product = modelMapper.map(command, Product.class);
    productVerificationService.verifyProduct(product);
    log.info(
        "Creating product {}, {}",
        product.getName(),
        userAuthenticationHelper.getUserId().orElse("N/A"));
    Product createdProduct = productPersistenceService.create(product);
    eventLogIntegrationService.sendProductEvent(product, EventType.CREATED, "Create Product");
    return createdProduct;
  }

  public Product updateProduct(UUID id, Long version, ProductUpdateCommand command) {
    Product product = getProduct(id);
    productAuthenticationService.hasWritePermission(product);
    modelMapper.map(command, product);
    productVerificationService.verifyProduct(product);
    Product updatedProduct = productPersistenceService.update(product, id, version);
    eventLogIntegrationService.sendProductEvent(updatedProduct, EventType.UPDATED, "Product updated");
    return updatedProduct;
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
    eventLogIntegrationService.sendProductEvent(Product.builder().id(id).build(), EventType.CANCELED, "Product deleted");
    productPersistenceService.delete(id, version);
  }


}
