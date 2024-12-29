package m7011e.the_homeric_odyssey.core.services.authorization.order;

import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.core.util.ResourceAuthorizationUtil;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderElevatedPrivilegePermission implements OrderAuthenticationRow {

  private final UserAuthenticationHelper userAuthenticationHelper;

  @Override
  public boolean hasReadPermission(Order resource) {
    return ResourceAuthorizationUtil.isAnElevatedRole(userAuthenticationHelper);
  }

  @Override
  public boolean hasWritePermission(Order resource) {
    return ResourceAuthorizationUtil.isAnElevatedRole(userAuthenticationHelper);
  }
}
