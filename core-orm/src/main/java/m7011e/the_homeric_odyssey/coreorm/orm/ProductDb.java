package m7011e.the_homeric_odyssey.coreorm.orm;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.ProductStatus;

@EqualsAndHashCode(callSuper = true)
@Table(
    name = "products",
    indexes = {
      @Index(name = "idx_product_name", columnList = "name"),
      @Index(name = "idx_product_status", columnList = "id, status"),
      @Index(name = "idx_product_price", columnList = "price")
    })
@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductDb extends AbstractDbObject {

  private String name;

  @Column(length = 1024)
  private String description;

  private Double price;

  @Enumerated(EnumType.STRING)
  private ProductStatus status;

  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<ResourceDb> categories;

  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<ResourceDb> documents;

  private String displayImage;

  private String companyLogo;

  private String contactEmail;

  private String contactPhone;

  private String contactFax;

  private String contactWebsite;

  private String contactAddress;

  @Column(nullable = false)
  private UUID sub;
}
