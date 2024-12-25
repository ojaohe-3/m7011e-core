package m7011e.the_homeric_odyssey.coreorm.configuration.orm;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.OrderStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(
    name = "orders",
    indexes = {
      @Index(name = "idx_order_user", columnList = "sub"),
      @Index(name = "idx_order_status", columnList = "status"),
      @Index(name = "idx_order_total", columnList = "totalPrice"),
      @Index(name = "idx_order_dates", columnList = "createdAt, updatedAt"),
      @Index(name = "idx_order_user_date", columnList = "sub, createdAt")
    })
@Entity
@Data
@RequiredArgsConstructor
public class OrderDB {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Version private Long version;

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  private UUID sub;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @ManyToMany(
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "product0")
  private Set<ProductDB> product;

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
