package m7011e.the_homeric_odyssey.core.services.authorization.order;

import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.core.util.ResourceAuthorizationUtil;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.resource_server.models.RealmUserType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderOwnerPermission implements OrderAuthenticationRow {

  private final UserAuthenticationHelper userAuthenticationHelper;

  @Override
  public boolean hasReadPermission(Order resource) {
    return  ResourceAuthorizationUtil.isOwner(userAuthenticationHelper, resource.getSub());
  }

  @Override
  public boolean hasWritePermission(Order resource) {
    return  ResourceAuthorizationUtil.isOwner(userAuthenticationHelper, resource.getSub());
  }
}
