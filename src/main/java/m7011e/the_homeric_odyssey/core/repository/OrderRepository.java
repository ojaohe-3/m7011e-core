package m7011e.the_homeric_odyssey.core.repository;

import java.util.UUID;
import m7011e.the_homeric_odyssey.coreorm.orm.OrderDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderDB, UUID> {}
