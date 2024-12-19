package m7011e.the_homeric_odyssey.modelsModule.models.domain;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class Product {
    private UUID id;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String description;
    private Double price;
    private Set<Category> categories;
}
