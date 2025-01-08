package m7011e.the_homeric_odyssey.core.controllers;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.core.services.product.ProductService;
import m7011e.the_homeric_odyssey.modelsModule.api.ProductApi;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController implements ProductApi {

  private final ProductService productService;

  @Override
  @PreAuthorize("hasAnyRole('VENDOR','ADMIN', 'SYSTEM')")
  public ResponseEntity<Product> createProduct(ProductCreateCommand command) {

    return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(command));
  }

  @Override
  @PreAuthorize("hasAnyRole('VENDOR','ADMIN', 'SYSTEM')")
  public ResponseEntity<Product> updateProduct(
      UUID id, Long version, ProductUpdateCommand command) {
    return ResponseEntity.ok(productService.updateProduct(id, version, command));
  }

  @Override
  @PreAuthorize("hasAnyRole('READ', 'ADMIN', 'SYSTEM')")
  public ResponseEntity<Product> getProduct(UUID id) {
    return ResponseEntity.ok(productService.getProduct(id));
  }

  @Override
  public ResponseEntity<Page<Product>> listProducts(ProductListCommand command) {
    Page<Product> items = productService.queryProducts(command);
    return ResponseEntity.ok(items);
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'SYSTEM', 'VENDOR')")
  public ResponseEntity<Void> deleteProduct(UUID id, Long version) {
    productService.deleteProduct(id, version);
    return ResponseEntity.noContent().build();
  }
}
