package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import java.time.LocalDateTime;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.OrderStatus;

public record OrderListCommand(
    Integer page,
    Integer size,
    String sortBy,
    String sortDirection,
    OrderStatus statusFilter,
    LocalDateTime startDate,
    LocalDateTime endDate) {}
