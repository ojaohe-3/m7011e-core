package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import m7011e.the_homeric_odyssey.modelsModule.models.domain.OrderStatus;

import java.util.UUID;

public record OrderStatusUpdateCommand(
        UUID orderId,
        OrderStatus newStatus,
        String statusNote,
        String updatedBy
) {}
