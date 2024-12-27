package m7011e.the_homeric_odyssey.core.services.product;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductPersistenceService productPersistenceService;

  private final ProductAuthenticationService productAuthenticationService;

  private final ModelMapper modelMapper;

  public Product getProduct(UUID id) {
    return productPersistenceService.get(id);
  }

  public Product createProduct(ProductCreateCommand command) {
    return null;
  }

  public Product updateProduct(UUID id, Long version, ProductUpdateCommand command) {
    Product product = getProduct(id);
    productAuthenticationService.hasWritePermission(product);
    modelMapper.map(command, product);

    return productPersistenceService.update(product, id, version);
  }

  public Page<Product> queryProducts(ProductListCommand command) {
    return productPersistenceService.query(command);
  }

  public void deleteProduct(UUID id, Long version) {}
}
