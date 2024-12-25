package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import java.util.Set;
import java.util.UUID;

public record ProductListCommand(
    Integer page,
    Integer size,
    String sortBy,
    String sortDirection,
    String nameFilter,
    Double minPrice,
    Double maxPrice,
    Set<UUID> categoryIds) {}
