package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.OrderStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateCommand {
  private UUID productId;

  private OrderStatus status;

  private Integer quantity;

  private String shippingAddress;

  private String billingAddress;

  private String contactEmail;

  private String contactPhone;

  private String paymentMethod;

  private String paymentDetails;

  private String paymentStatus;

  private String transactionId;

  private LocalDateTime paidAt;

  private LocalDateTime shippedAt;

  private LocalDateTime deliveredAt;

  private LocalDateTime cancelledAt;
}
