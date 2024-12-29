package m7011e.the_homeric_odyssey.core.controllers;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.core.services.session.CartService;
import m7011e.the_homeric_odyssey.modelsModule.api.CartApi;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.CartCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.CartItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController implements CartApi {
  private final CartService cartService;

  @PostMapping
  @Override
  public ResponseEntity<CartItem> addToCart(@RequestBody CartCommand command) {
    return ResponseEntity.ok(cartService.addToCart(command));
  }

  @PutMapping("/{itemId}")
  @Override
  public ResponseEntity<CartItem> updateCartItem(
      @PathVariable UUID itemId, @RequestBody CartCommand command) {
    return ResponseEntity.ok(cartService.updateCartItem(itemId, command));
  }

  @DeleteMapping("/{sub}/{itemId}")
  @Override
  public ResponseEntity<Void> removeFromCart(@PathVariable UUID sub, @PathVariable UUID itemId) {
    cartService.removeFromCart(sub, itemId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{sub}/{itemId}")
  @Override
  public ResponseEntity<CartItem> getCartItem(@PathVariable UUID sub, @PathVariable UUID itemId) {
    return ResponseEntity.ok(cartService.getCartItem(sub, itemId));
  }

  @GetMapping("/{sub}")
  @Override
  public ResponseEntity<List<CartItem>> getAllCartItems(@PathVariable UUID sub) {
    return ResponseEntity.ok(cartService.getAllCartItems(sub));
  }
}
