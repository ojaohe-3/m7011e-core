package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import java.util.UUID;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.OrderStatus;

public record OrderStatusUpdateCommand(
    UUID orderId, OrderStatus newStatus, String statusNote, String updatedBy) {}
