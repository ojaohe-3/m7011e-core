package m7011e.the_homeric_odyssey.core.repository;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import m7011e.the_homeric_odyssey.coreorm.orm.OrderDB;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.OrderStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderDB, UUID> {
  List<OrderDB> findByStatus(OrderStatus status);

  @Query("SELECT o FROM OrderDB o WHERE " + "o.createdAt BETWEEN :startDate AND :endDate")
  List<OrderDB> findByDateRange(
      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

  default Specification<OrderDB> createFilterSpecification(OrderListCommand command) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (command.statusFilter() != null) {
        predicates.add(cb.equal(root.get("status"), command.statusFilter()));
      }

      if (command.startDate() != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), command.startDate()));
      }

      if (command.endDate() != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), command.endDate()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
