package m7011e.the_homeric_odyssey.core.configuration;

import m7011e.the_homeric_odyssey.coreorm.orm.OrderDb;
import m7011e.the_homeric_odyssey.coreorm.orm.ProductDb;
import m7011e.the_homeric_odyssey.coreorm.orm.ResourceDb;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.CartCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderStatusUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.CartItem;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

  public static void configureModelMapper(ModelMapper modelMapper) {
    modelMapper.typeMap(CartCommand.class, CartItem.class);
    modelMapper.typeMap(OrderCreateCommand.class, Order.class);
    modelMapper.typeMap(OrderStatusUpdateCommand.class, Order.class);
    modelMapper.typeMap(ProductCreateCommand.class, Product.class);
    modelMapper.typeMap(ProductUpdateCommand.class, Product.class);

    modelMapper.typeMap(Resource.class, ResourceDb.class);
    modelMapper.typeMap(ResourceDb.class, Resource.class);

    modelMapper.typeMap(ProductDb.class, Product.class);
    modelMapper.typeMap(Product.class, ProductDb.class);

    modelMapper.typeMap(OrderDb.class, Order.class);
    modelMapper.typeMap(Order.class, OrderDb.class);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
