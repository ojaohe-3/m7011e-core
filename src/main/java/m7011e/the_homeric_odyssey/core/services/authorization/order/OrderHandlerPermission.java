package m7011e.the_homeric_odyssey.core.services.authorization.order;

import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.core.util.ResourceAuthorizationUtil;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.resource_server.models.RealmUserType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderHandlerPermission implements OrderAuthenticationRow {

  private final UserAuthenticationHelper userAuthenticationHelper;

  @Override
  public boolean hasReadPermission(Order resource) {
    return hasAccessToAProduct(resource)
        && userAuthenticationHelper.hasWriteRole(RealmUserType.VENDOR);
  }

  @Override
  public boolean hasWritePermission(Order resource) {
    return hasAccessToAProduct(resource)
        && userAuthenticationHelper.hasWriteRole(RealmUserType.VENDOR);
  }

  private boolean hasAccessToAProduct(Order resource) {
    return resource.getProduct().stream()
        .anyMatch(
            product ->
                ResourceAuthorizationUtil.isOwner(userAuthenticationHelper, resource.getSub()));
  }
}
