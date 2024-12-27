package m7011e.the_homeric_odyssey.core.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import m7011e.the_homeric_odyssey.coreorm.orm.ProductDB;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductDB, UUID> {
  default Specification<ProductDB> createFilterSpecification(ProductListCommand command) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (command.getNameFilter() != null) {
        predicates.add(
            cb.like(cb.lower(root.get("name")), "%" + command.getNameFilter().toLowerCase() + "%"));
      }

      if (command.getMinPrice() != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("price"), command.getMinPrice()));
      }

      if (command.getMaxPrice() != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get("price"), command.getMaxPrice()));
      }

      if (CollectionUtils.isNotEmpty(command.getCategoryIds())) {
        Join<Product, Resource> categoryJoin = root.join("categories");
        predicates.add(categoryJoin.get("id").in(command.getCategoryIds()));
      }

      if (CollectionUtils.isNotEmpty(command.getStatuses())) {
        predicates.add(cb.in(root.get("status")).value(command.getStatuses()));
      }

      if (command.getSub() != null){
        predicates.add(cb.equal(root.get("sub"), command.getSub()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
