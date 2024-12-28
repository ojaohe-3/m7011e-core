package m7011e.the_homeric_odyssey.core.configuration;

import m7011e.the_homeric_odyssey.coreorm.orm.OrderDB;
import m7011e.the_homeric_odyssey.coreorm.orm.ProductDB;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.CartCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderStatusUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.CartItem;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
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

    modelMapper.typeMap(ProductDB.class, Product.class);
    modelMapper.typeMap(Product.class, ProductDB.class);

    modelMapper.typeMap(OrderDB.class, Order.class);
    modelMapper.typeMap(Order.class, OrderDB.class);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
