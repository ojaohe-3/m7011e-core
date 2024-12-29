package m7011e.the_homeric_odyssey.core.util;

import java.util.Objects;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.resource_server.models.RealmUserType;

@UtilityClass
public class ResourceAuthorizationUtil {

  public boolean isOwner(UserAuthenticationHelper userAuthenticationHelper, UUID sub) {
    UUID scopeId = null;
    if (userAuthenticationHelper.getUserScope().isPresent()) {
      scopeId = UUID.fromString(userAuthenticationHelper.getUserScope().get());
    }
    UUID userId = null;
    if (userAuthenticationHelper.getUserId().isPresent()) {
      userId = UUID.fromString(userAuthenticationHelper.getUserId().get());
    }

    return Objects.equals(scopeId, sub) || Objects.equals(userId, sub);
  }

  public boolean isAnElevatedRole(UserAuthenticationHelper userAuthenticationHelper) {
    return userAuthenticationHelper.hasReadRole(RealmUserType.ADMIN)
            || userAuthenticationHelper.hasReadRole(RealmUserType.SYSTEM);
  }
}
