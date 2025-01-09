package m7011e.the_homeric_odyssey.core.services.authorization.product;

import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.core.util.ResourceAuthorizationUtil;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import m7011e.the_homeric_odyssey.resource_server.models.RealmUserType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductOwner implements ProductAuthenticationRow {

  private final UserAuthenticationHelper userAuthenticationHelper;

  @Override
  public boolean hasReadPermission(Product resource) {
    return userAuthenticationHelper.hasReadRole(RealmUserType.VENDOR)
        && ResourceAuthorizationUtil.isOwner(userAuthenticationHelper, resource.getSub());

  }

  @Override
  public boolean hasWritePermission(Product resource) {
    return userAuthenticationHelper.hasWriteRole(RealmUserType.VENDOR)
        && ResourceAuthorizationUtil.isOwner(userAuthenticationHelper, resource.getSub());
  }
}
