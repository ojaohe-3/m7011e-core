package m7011e.the_homeric_odyssey.core.services.authorization.product;

import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.core.util.ResourceAuthorizationUtil;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductElevatedPrivilegePermission implements ProductAuthenticationRow{

    private final UserAuthenticationHelper userAuthenticationHelper;

    @Override
    public boolean hasReadPermission(Product resource) {
        return ResourceAuthorizationUtil.isAnElevatedRole(userAuthenticationHelper);
    }

    @Override
    public boolean hasWritePermission(Product resource) {
        return ResourceAuthorizationUtil.isAnElevatedRole(userAuthenticationHelper);

    }
}
