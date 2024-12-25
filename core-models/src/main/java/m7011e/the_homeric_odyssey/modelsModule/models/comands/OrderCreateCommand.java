package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import java.util.UUID;

public record OrderCreateCommand(
    UUID productId,
    Integer quantity,
    String shippingAddress,
    String billingAddress,
    String contactEmail,
    String contactPhone,
    String paymentMethod,
    String paymentDetails // This could be a token or other payment-related info
    ) {}
