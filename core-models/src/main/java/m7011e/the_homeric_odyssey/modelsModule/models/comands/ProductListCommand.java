package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.ProductStatus;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductListCommand {
  private Integer page;
  private Integer size;
  private String sortBy;
  private Sort.Direction sortDirection;
  private String nameFilter;
  private Double minPrice;
  private Double maxPrice;
  private Set<ProductStatus> statuses;
  private Set<UUID> categoryIds;
  private UUID sub;
}
