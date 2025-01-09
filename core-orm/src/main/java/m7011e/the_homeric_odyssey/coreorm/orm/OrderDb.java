package m7011e.the_homeric_odyssey.coreorm.orm;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.OrderStatus;

@EqualsAndHashCode(callSuper = true)
@Table(
    name = "orders",
    indexes = {
      @Index(name = "fk_idx_order_product", columnList = "product_id"),
      @Index(name = "idx_order_user", columnList = "sub"),
      @Index(name = "idx_order_status", columnList = "status"),
      @Index(name = "idx_order_total", columnList = "totalPrice"),
      @Index(name = "idx_order_dates", columnList = "createdAt, updatedAt"),
      @Index(name = "idx_order_user_date", columnList = "sub, createdAt")
    })
@Entity
@Data
@RequiredArgsConstructor
public class OrderDb extends AbstractDbObject {

  private UUID sub;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(name = "product_id")
  private UUID productId;

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
