package m7011e.the_homeric_odyssey.modelsModule.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderStatusUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Orders", description = "Order management API endpoints")
public interface OrderApi {

  @Operation(
      summary = "Create new order",
      description = "Places a new order with the provided details")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Order successfully created",
            content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Product not found")
      })
  @PostMapping
  ResponseEntity<Order> createOrder(
      @Parameter(description = "Order creation details", required = true) @RequestBody
          OrderCreateCommand command);

  @Operation(
      summary = "Update order status",
      description = "Updates the status of an existing order")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Order status successfully updated",
        content = @Content(schema = @Schema(implementation = Order.class))),
    @ApiResponse(responseCode = "404", description = "Order not found")
  })
  @PutMapping("/{orderId}/status")
  ResponseEntity<Order> updateOrderStatus(
      @Parameter(description = "Order ID", required = true) @PathVariable UUID orderId,
      @Parameter(description = "Order status update details", required = true) @RequestBody
          OrderStatusUpdateCommand command);

  @Operation(summary = "Get order", description = "Retrieves a specific order by ID")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Order found",
        content = @Content(schema = @Schema(implementation = Order.class))),
    @ApiResponse(responseCode = "404", description = "Order not found")
  })
  @GetMapping("/{orderId}")
  ResponseEntity<Order> getOrder(
      @Parameter(description = "Order ID", required = true) @PathVariable UUID orderId);

  @Operation(
      summary = "List orders",
      description = "Retrieves a paginated list of orders with optional filtering")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Orders retrieved successfully",
        content = @Content(schema = @Schema(implementation = Page.class)))
  })
  @GetMapping
  ResponseEntity<Page<Order>> listOrders(
      @Parameter(description = "Order listing parameters", required = true) @ModelAttribute
          OrderListCommand command);

  @Operation(summary = "Cancel order", description = "Cancels a specific order by ID")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Order successfully cancelled",
        content = @Content(schema = @Schema(implementation = Order.class))),
    @ApiResponse(responseCode = "404", description = "Order not found"),
    @ApiResponse(responseCode = "400", description = "Order cannot be cancelled in current state")
  })
  @PostMapping("/{orderId}/cancel")
  ResponseEntity<Order> cancelOrder(
      @Parameter(description = "Order ID", required = true) @PathVariable UUID orderId);
}
