package m7011e.the_homeric_odyssey.core.repository;

import m7011e.the_homeric_odyssey.coreorm.orm.ProductDb;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductQueryRepository extends JpaSpecificationExecutor<ProductDb> {

}
