package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.ProductStatus;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Resource;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateCommand {
  private String name;

  private String description;

  private ProductStatus status;

  private Double price;

  private Set<Resource> categories;

  private Set<Resource> documents;

  private String displayImage;

  private String companyLogo;

  private String contactEmail;

  private String contactPhone;

  private String contactFax;

  private String contactWebsite;

  private String contactAddress;
}
