package m7011e.the_homeric_odyssey.core.configuration;

import java.util.Objects;
import java.util.UUID;
import m7011e.the_homeric_odyssey.core.repository.ProductRepository;
import m7011e.the_homeric_odyssey.coreorm.orm.OrderDb;
import m7011e.the_homeric_odyssey.coreorm.orm.ProductDb;
import m7011e.the_homeric_odyssey.coreorm.orm.ResourceDb;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.CartCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.CartItem;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Resource;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

  public static void configureModelMapper(
      ModelMapper modelMapper) {
    modelMapper
        .getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT) // Use strict matching
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
        .setPropertyCondition(Conditions.isNotNull()) // Skip null values
        .setSkipNullEnabled(true) // Skip null values during mapping
        .setAmbiguityIgnored(true);

    configureOrmClasses(modelMapper);
    configureCommands(modelMapper);
  }

  private static void configureCommands(
      ModelMapper modelMapper) {
    modelMapper
        .typeMap(CartCommand.class, CartItem.class)
        .addMappings(
            mapping -> {
              mapping.skip(CartItem::setId);
              mapping.skip(CartItem::setVersion);
            });
    modelMapper
        .typeMap(OrderCreateCommand.class, Order.class)
        .addMappings(
            mapping -> {
              mapping.skip(Order::setId);
              mapping.skip(Order::setVersion);
              mapping.skip(Order::setCreatedAt);
              mapping.skip(Order::setUpdatedAt);
              mapping.skip(Order::setTotalPrice);
              mapping.skip(Order::setSub);
            });
    modelMapper
        .typeMap(ProductCreateCommand.class, Product.class)
        .addMappings(
            mapping -> {
              mapping.skip(Product::setId);
              mapping.skip(Product::setVersion);
              mapping.skip(Product::setCreatedAt);
              mapping.skip(Product::setUpdatedAt);
              mapping.skip(Product::setSub);
            });
    modelMapper
        .typeMap(ProductUpdateCommand.class, Product.class)
        .addMappings(
            mapping -> {
              mapping.skip(Product::setId);
              mapping.skip(Product::setVersion);
              mapping.skip(Product::setCreatedAt);
              mapping.skip(Product::setUpdatedAt);
              mapping.skip(Product::setSub);
            });
  }

  private static void configureOrmClasses(ModelMapper modelMapper) {
    modelMapper.typeMap(Resource.class, ResourceDb.class);
    modelMapper.typeMap(ResourceDb.class, Resource.class);

    modelMapper.typeMap(ProductDb.class, Product.class);
    modelMapper.typeMap(Product.class, ProductDb.class);

    modelMapper.typeMap(OrderDb.class, Order.class);
    modelMapper.typeMap(Order.class, OrderDb.class);
  }

  private static Converter<UUID, Product> fetchProduct(
      ModelMapper modelMapper, ProductRepository repository) {
    return context -> {
      if (Objects.isNull(context.getSource())) {
        return null;
      }
      ProductDb productDb = repository.findById(context.getSource()).orElse(null);
      return modelMapper.map(productDb, Product.class);
    };
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    configureModelMapper(modelMapper);
    return modelMapper;
  }
}
