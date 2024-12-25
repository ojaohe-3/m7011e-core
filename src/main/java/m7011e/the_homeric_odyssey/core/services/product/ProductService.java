package m7011e.the_homeric_odyssey.core.services.product;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.core.repository.ProductRepository;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductPersistenceService productPersistenceService;

  public Product getProduct(UUID id) {
    return null;
  }
}
