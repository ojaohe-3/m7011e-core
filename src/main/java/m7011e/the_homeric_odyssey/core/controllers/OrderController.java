package m7011e.the_homeric_odyssey.core.controllers;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.modelsModule.api.OrderApi;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderStatusUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController implements OrderApi {
  @Override
  public ResponseEntity<Order> createOrder(OrderCreateCommand command) {
    return null;
  }

  @Override
  public ResponseEntity<Order> updateOrderStatus(UUID orderId, OrderStatusUpdateCommand command) {
    return null;
  }

  @Override
  public ResponseEntity<Order> getOrder(UUID orderId) {
    return null;
  }

  @Override
  public ResponseEntity<Page<Order>> listOrders(OrderListCommand command) {
    return null;
  }

  @Override
  public ResponseEntity<Order> cancelOrder(UUID orderId) {
    return null;
  }
}
