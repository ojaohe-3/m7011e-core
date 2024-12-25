package m7011e.the_homeric_odyssey.modelsModule.models.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class CartItem {
    private UUID id;
    private Long version;
    private Product product;
    private Integer quantity;
    private Double price;
    private UUID sub;
}
