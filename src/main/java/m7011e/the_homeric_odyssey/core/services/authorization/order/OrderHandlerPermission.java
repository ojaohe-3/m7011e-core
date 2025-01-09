package m7011e.the_homeric_odyssey.core.services.authorization.order;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.core.repository.ProductRepository;
import m7011e.the_homeric_odyssey.core.util.ResourceAuthorizationUtil;
import m7011e.the_homeric_odyssey.coreorm.orm.ProductDb;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.resource_server.models.RealmUserType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderHandlerPermission implements OrderAuthenticationRow {

  private final UserAuthenticationHelper userAuthenticationHelper;

  private final ProductRepository productRepository;

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
    return Objects.nonNull(resource.getProductId())
        && ResourceAuthorizationUtil.isOwner(
            userAuthenticationHelper,
            productRepository.findById(resource.getProductId()).map(ProductDb::getSub).orElseThrow());
  }
}
