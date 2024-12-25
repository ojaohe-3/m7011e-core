package m7011e.the_homeric_odyssey.modelsModule.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.CartCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.CartItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Cart", description = "Cart management API endpoints")
public interface CartApi {

  @Operation(
      summary = "Add item to cart",
      description = "Adds a new item to the user's shopping cart")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Item successfully added to cart",
        content = @Content(schema = @Schema(implementation = CartItem.class))),
    @ApiResponse(responseCode = "400", description = "Invalid input data"),
    @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @PostMapping
  ResponseEntity<CartItem> addToCart(
      @Parameter(description = "Cart item details", required = true) @RequestBody
          CartCommand command);

  @Operation(
      summary = "Update cart item",
      description = "Updates the quantity or other details of an existing cart item")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Cart item successfully updated",
        content = @Content(schema = @Schema(implementation = CartItem.class))),
    @ApiResponse(responseCode = "404", description = "Cart item not found")
  })
  @PutMapping("/{itemId}")
  ResponseEntity<CartItem> updateCartItem(
      @Parameter(description = "Cart item ID", required = true) @PathVariable UUID itemId,
      @Parameter(description = "Updated cart item details", required = true) @RequestBody
          CartCommand command);

  @Operation(
      summary = "Remove item from cart",
      description = "Removes an item from the user's shopping cart")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Cart item successfully removed"),
    @ApiResponse(responseCode = "404", description = "Cart item not found")
  })
  @DeleteMapping("/{sub}/{itemId}")
  ResponseEntity<Void> removeFromCart(
      @Parameter(description = "User identifier", required = true) @PathVariable UUID sub,
      @Parameter(description = "Cart item ID", required = true) @PathVariable UUID itemId);

  @Operation(
      summary = "Get cart item",
      description = "Retrieves a specific item from the user's shopping cart")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Cart item found",
        content = @Content(schema = @Schema(implementation = CartItem.class))),
    @ApiResponse(responseCode = "404", description = "Cart item not found")
  })
  @GetMapping("/{sub}/{itemId}")
  ResponseEntity<CartItem> getCartItem(
      @Parameter(description = "User identifier", required = true) @PathVariable UUID sub,
      @Parameter(description = "Cart item ID", required = true) @PathVariable UUID itemId);

  @Operation(
      summary = "Get all cart items",
      description = "Retrieves all items from the user's shopping cart")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "List of cart items retrieved successfully",
        content = @Content(schema = @Schema(implementation = CartItem.class)))
  })
  @GetMapping("/{sub}")
  ResponseEntity<List<CartItem>> getAllCartItems(
      @Parameter(description = "User identifier", required = true) @PathVariable UUID sub);
}
