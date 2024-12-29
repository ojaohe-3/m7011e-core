package m7011e.the_homeric_odyssey.coreorm.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "m7011e.the_homeric_odyssey.coreorm")
@EntityScan(basePackages = "m7011e.the_homeric_odyssey.coreorm.orm")
public class CoreOrmAutoConfiguration {

}
