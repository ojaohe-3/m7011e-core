package m7011e.the_homeric_odyssey.core.controllers;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.core.services.order.OrderService;
import m7011e.the_homeric_odyssey.modelsModule.api.OrderApi;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderStatusUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController implements OrderApi {

  private final OrderService orderService;

  @Override
  public ResponseEntity<Order> createOrder(OrderCreateCommand command) {
    return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(command));
  }

  @Override
  public ResponseEntity<Order> updateOrderStatus(UUID orderId, Long version, OrderStatusUpdateCommand command) {
    return ResponseEntity.ok(orderService.updateOrderStatus(command, orderId, version));
  }

  @Override
  public ResponseEntity<Order> getOrder(UUID orderId) {
    return ResponseEntity.ok(orderService.getOrder(orderId));
  }

  @Override
  public ResponseEntity<Page<Order>> listOrders(OrderListCommand command) {
    return ResponseEntity.ok(orderService.queryOrders(command));
  }

  @Override
  public ResponseEntity<Order> cancelOrder(UUID orderId, Long version) {
    return ResponseEntity.ok(orderService.cancelOrder(orderId, version));
  }

}
