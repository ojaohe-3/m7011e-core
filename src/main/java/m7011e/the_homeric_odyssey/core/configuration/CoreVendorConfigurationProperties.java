package m7011e.the_homeric_odyssey.core.configuration;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.ProductStatus;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "core-app.application")
@Validated
@Getter
@Setter
public class CoreVendorConfigurationProperties {
    private Set<ProductStatus> productPubliclyAvailable = Set.of(ProductStatus.AVAILABLE,
            ProductStatus.PREORDER,
            ProductStatus.EMPTY);
}
