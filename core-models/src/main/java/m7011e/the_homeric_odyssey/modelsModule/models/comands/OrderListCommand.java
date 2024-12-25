package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import m7011e.the_homeric_odyssey.modelsModule.models.domain.OrderStatus;

import java.time.LocalDateTime;

public record OrderListCommand(
        Integer page,
        Integer size,
        String sortBy,
        String sortDirection,
        OrderStatus statusFilter,
        LocalDateTime startDate,
        LocalDateTime endDate
) {}
