package m7011e.the_homeric_odyssey.core.services.order;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.core.services.authorization.order.OrderAuthenticationRow;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderAuthenticationService {

  private final UserAuthenticationHelper authenticationHelper;

  private final Set<OrderAuthenticationRow> authenticationRowPermissionSet;

  public boolean hasReadPermission(Order item) {
    return authenticationRowPermissionSet.stream()
        .anyMatch(permission -> permission.hasReadPermission(item));
  }

  public boolean hasWritePermission(Order item) {
    return authenticationRowPermissionSet.stream()
        .anyMatch(permission -> permission.hasWritePermission(item));
  }
}
