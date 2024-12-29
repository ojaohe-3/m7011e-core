package m7011e.the_homeric_odyssey.core.configuration;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.ProductStatus;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "application")
@Validated
@Getter
@Setter
public class CoreVendorConfigurationProperties {
    private @NotEmpty Set<ProductStatus> productPubliclyAvailable;
}
