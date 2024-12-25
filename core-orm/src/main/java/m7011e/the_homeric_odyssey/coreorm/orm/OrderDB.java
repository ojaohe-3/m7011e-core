package m7011e.the_homeric_odyssey.coreorm.orm;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.OrderStatus;

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
public class OrderDB extends AbstractDbObject {

  private UUID sub;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @ManyToMany(
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.LAZY)
  @JoinTable(name = "orders_product",
  joinColumns = @JoinColumn(name = "product_id"),
  inverseJoinColumns = @JoinColumn(name = "order_id"))
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
