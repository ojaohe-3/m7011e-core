package m7011e.the_homeric_odyssey.core.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import m7011e.the_homeric_odyssey.coreorm.orm.ProductDB;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductDB, UUID> {
    @Query("SELECT p FROM ProductDB p WHERE " +
            "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<ProductDB> findByFilters(
            @Param("name") String name,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );

    @Query("SELECT DISTINCT p FROM ProductDB p " +
            "JOIN p.categories c " +
            "WHERE c.id IN :categoryIds")
    List<ProductDB> findByCategories(@Param("categoryIds") Set<UUID> categoryIds);

    default Specification<ProductDB> createFilterSpecification(ProductListCommand command) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (command.nameFilter() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + command.nameFilter().toLowerCase() + "%"
                ));
            }

            if (command.minPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("price"),
                        command.minPrice()
                ));
            }

            if (command.maxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("price"),
                        command.maxPrice()
                ));
            }

            if (command.categoryIds() != null && !command.categoryIds().isEmpty()) {
                Join<Product, Resource> categoryJoin = root.join("categories");
                predicates.add(categoryJoin.get("id").in(command.categoryIds()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
