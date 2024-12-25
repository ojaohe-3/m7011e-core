package m7011e.the_homeric_odyssey.modelsModule.models.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Order {

    private UUID id;

    private Long version;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UUID sub;

    private OrderStatus status;

    private Set<Product> product;

    private Integer quantity;

    private Double totalPrice;

    private String shippingAddress;

    private String billingAddress;

    private String contactEmail;

    private String contactPhone;

    private String paymentMethod;

    private String paymentStatus;

    private String transactionId;

    private LocalDateTime paidAt;

    private LocalDateTime shippedAt;

    private LocalDateTime deliveredAt;

    private LocalDateTime cancelledAt;
}
