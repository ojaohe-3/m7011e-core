package m7011e.the_homeric_odyssey.core.repository;

import java.util.UUID;

import m7011e.the_homeric_odyssey.coreorm.orm.ProductDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductDB, UUID> {}
