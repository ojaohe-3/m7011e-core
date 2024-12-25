package m7011e.the_homeric_odyssey.core.controllers;

import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.modelsModule.api.ProductApi;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    @Override
    @PreAuthorize("hasAnyRole('VENDOR','ADMIN', 'WRITE', 'READ')")

    public ResponseEntity<Product> createProduct(ProductCreateCommand command) {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyRole('VENDOR','ADMIN', 'WRITE', 'READ')")
    public ResponseEntity<Product> updateProduct(UUID id, ProductUpdateCommand command) {
        return null;
    }

    @Override
    public ResponseEntity<Product> getProduct(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Page<Product>> listProducts(ProductListCommand command) {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyRole('VENDOR','ADMIN', 'WRITE', 'READ')")
    public ResponseEntity<Void> deleteProduct(UUID id) {
        return null;
    }
}
