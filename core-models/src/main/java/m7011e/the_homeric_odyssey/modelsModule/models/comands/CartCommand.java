package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import lombok.Data;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;

import java.util.UUID;

@Data
public class CartCommand {
    private Product product;
    private Integer quantity;
    private Double price;
    private UUID sub;
}
