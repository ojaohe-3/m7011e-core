package m7011e.the_homeric_odyssey.core.services.product;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.core.services.authorization.product.ProductAuthenticationRow;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAuthenticationService{

    private final UserAuthenticationHelper authenticationHelper;

    private final Set<ProductAuthenticationRow> authenticationRowPermissionSet;

    public boolean hasReadPermission(Product item) {
        return authenticationRowPermissionSet.stream().anyMatch(permission -> permission.hasReadPermission(item));
    }

    public boolean hasWritePermission(Product item) {
        return authenticationRowPermissionSet.stream().anyMatch(permission -> permission.hasWritePermission(item));
    }

}
