package m7011e.the_homeric_odyssey.core.repository;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import m7011e.the_homeric_odyssey.coreorm.orm.OrderDb;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderListCommand;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
    extends JpaRepository<OrderDb, UUID>, JpaSpecificationExecutor<OrderDb> {
  default Specification<OrderDb> createFilterSpecification(OrderListCommand command) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (command.getStatusFilter() != null) {
        predicates.add(cb.equal(root.get("status"), command.getStatusFilter()));
      }

      if (command.getStartDate() != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), command.getStartDate()));
      }

      if (command.getEndDate() != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), command.getEndDate()));
      }

      if (command.getSub() != null) {
        predicates.add(cb.equal(root.get("sub"), command.getSub()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
