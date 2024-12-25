package m7011e.the_homeric_odyssey.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.CartCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.CartItem;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import m7011e.the_homeric_odyssey.resource_server.exceptions.ResourceNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private static final String CART_KEY_PREFIX = "cart:";
    private final UserAuthenticationHelper userAuthenticationHelper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ModelMapper modelMapper;
    private final ProductService productService; // Assume this exists to fetch product details

    private String getCartKey(UUID sub) {
        return CART_KEY_PREFIX + sub.toString();
    }

    public CartItem addToCart(@NotNull CartCommand command) {
        Product product = productService.getProduct(command.getProduct().getId());

        CartItem cartItem = new CartItem();
        modelMapper.map(command, cartItem);
        cartItem.setId(UUID.randomUUID());
        cartItem.setProduct(product);
        cartItem.setPrice(product.getPrice() * command.getQuantity());
        cartItem.setVersion(1L);

        String cartKey = getCartKey(UUID.fromString(userAuthenticationHelper.getUserId().orElseThrow())); // TODO FIX NON-LOGINs
        redisTemplate.opsForHash().put(cartKey, cartItem.getId().toString(), cartItem);

        return cartItem;
    }

    public CartItem updateCartItem(UUID itemId, CartCommand command) {
        String cartKey = getCartKey(UUID.fromString(userAuthenticationHelper.getUserId().orElseThrow()));
        CartItem existingItem = (CartItem) redisTemplate.opsForHash().get(cartKey, itemId.toString());

        if (existingItem == null) {
            throw new ResourceNotFoundException("Cart item not found");
        }

        existingItem.setQuantity(command.getQuantity());
        existingItem.setPrice(existingItem.getProduct().getPrice() * command.getQuantity());
        existingItem.setVersion(existingItem.getVersion() + 1);

        redisTemplate.opsForHash().put(cartKey, itemId.toString(), existingItem);

        return existingItem;
    }

    public void removeFromCart(UUID sub, UUID itemId) {
        String cartKey = getCartKey(sub);
        redisTemplate.opsForHash().delete(cartKey, itemId.toString());
    }

    public CartItem getCartItem(UUID sub, UUID itemId) {
        String cartKey = getCartKey(sub);
        CartItem item = (CartItem) redisTemplate.opsForHash().get(cartKey, itemId.toString());

        if (item == null) {
            throw new ResourceNotFoundException("Cart item not found");
        }

        return item;
    }

    public List<CartItem> getAllCartItems(UUID sub) {
        String cartKey = getCartKey(sub);
        return new ArrayList<>(redisTemplate.opsForHash()
                .values(cartKey)
                .stream()
                .map(obj -> (CartItem) obj)
                .toList());
    }
}
