package m7011e.the_homeric_odyssey.core.services.product;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.core.repository.ProductQueryRepository;
import m7011e.the_homeric_odyssey.core.repository.ProductRepository;
import m7011e.the_homeric_odyssey.core.services.IPersistenceService;
import m7011e.the_homeric_odyssey.core.util.VersionUtil;
import m7011e.the_homeric_odyssey.coreorm.orm.ProductDB;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import m7011e.the_homeric_odyssey.resource_server.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductPersistenceService implements IPersistenceService<ProductDB, Product> {

  private final ModelMapper modelMapper;

  private final ProductRepository productRepository;

  private final ProductQueryRepository productQueryRepository;

  @Override
  public Product create(Product entity) {
    return mapToDomain(productRepository.saveAndFlush(mapToDB(entity)));
  }

  @Override
  public Product update(Product entity, UUID id, Long version) {
    VersionUtil.validateVersion(
        version, productRepository.findById(id).orElseThrow(ResourceNotFoundException::new));

    return create(entity);
  }

  @Override
  public void delete(UUID id, Long version) {
    VersionUtil.validateVersion(
        version, productRepository.findById(id).orElseThrow(ResourceNotFoundException::new));

    productRepository.deleteById(id);
  }

  @Override
  public Product get(UUID id) {
    return productRepository
        .findById(id)
        .map(this::mapToDomain)
        .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public List<Product> getAll() {
    return productRepository.findAll().stream().map(this::mapToDomain).toList();
  }

  @Override
  public Product mapToDomain(ProductDB entity) {
    return modelMapper.map(entity, Product.class);
  }

  @Override
  public ProductDB mapToDB(Product product) {
    return modelMapper.map(product, ProductDB.class);
  }

  public Page<Product> query(ProductListCommand command) {
    Pageable page =
        PageRequest.of(
            command.getPage(),
            command.getSize(),
            Sort.by(
                command.getSortDirection(),
                command.getSortBy() == null ? "createdAt" : command.getSortBy()));

    Specification<ProductDB> specification = productRepository.createFilterSpecification(command);
    return productQueryRepository.findAll(specification, page).map(this::mapToDomain);
  }
}
