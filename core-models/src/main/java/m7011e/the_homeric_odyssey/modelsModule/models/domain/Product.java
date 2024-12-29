package m7011e.the_homeric_odyssey.modelsModule.models.domain;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {
  private UUID id;
  private Long version;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String name;
  private String description;
  private Double price;
  private ProductStatus status;
  private Set<Resource> categories;
  private Set<Resource> documents;
  private String displayImage;
  private String companyLogo;
  private String contactEmail;
  private String contactPhone;
  private String contactFax;
  private String contactWebsite;
  private String contactAddress;
  private UUID sub;
}
