package m7011e.the_homeric_odyssey.modelsModule.configurations;

import m7011e.the_homeric_odyssey.modelsModule.models.comands.ChartCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.ChartItem;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(ChartCommand.class, ChartItem.class);
        modelMapper.typeMap(OrderCommand.class, Order.class);
        modelMapper.typeMap(ProductCommand.class, Product.class);

        return modelMapper;
    }
}
