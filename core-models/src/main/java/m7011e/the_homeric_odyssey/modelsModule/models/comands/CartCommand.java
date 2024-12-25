package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import java.util.UUID;
import lombok.Data;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;

@Data
public class CartCommand {
  private Product product;
  private Integer quantity;
  private Double price;
  private UUID sub;
}
