package m7011e.the_homeric_odyssey.core.configuration;

import java.util.Objects;
import java.util.UUID;
import m7011e.the_homeric_odyssey.core.repository.ProductRepository;
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
import org.jetbrains.annotations.NotNull;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

  @Autowired private ProductRepository productRepository;

  public static void configureModelMapper(
      ModelMapper modelMapper, ProductRepository productRepository) {
    modelMapper
        .getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT) // Use strict matching
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
        .setPropertyCondition(Conditions.isNotNull()) // Skip null values
        .setSkipNullEnabled(true) // Skip null values during mapping
        .setAmbiguityIgnored(true);

    configureOrmClasses(modelMapper);
    configureCommands(modelMapper, productRepository);
  }

  private static void configureCommands(
      ModelMapper modelMapper, ProductRepository productRepository) {
    modelMapper.typeMap(CartCommand.class, CartItem.class);
    modelMapper
        .typeMap(OrderCreateCommand.class, Order.class)
        .addMappings(getOrderSkipPropertyMap())
        .addMappings(
            mapping -> {
              mapping
                  .using(fetchProduct(modelMapper, productRepository))
                  .map(OrderCreateCommand::getProductId, Order::setProduct);
            });
    modelMapper.typeMap(ProductCreateCommand.class, Product.class);
    modelMapper.typeMap(ProductUpdateCommand.class, Product.class);
  }

  private static void configureOrmClasses(ModelMapper modelMapper) {
    modelMapper.typeMap(Resource.class, ResourceDb.class);
    modelMapper.typeMap(ResourceDb.class, Resource.class);

    modelMapper.typeMap(ProductDb.class, Product.class);
    modelMapper.typeMap(Product.class, ProductDb.class);

    modelMapper.typeMap(OrderDb.class, Order.class);
    modelMapper.typeMap(Order.class, OrderDb.class);
  }

  @NotNull
  private static PropertyMap<OrderCreateCommand, Order> getOrderSkipPropertyMap() {
    return new PropertyMap<OrderCreateCommand, Order>() {
      @Override
      protected void configure() {
        skip(destination.getId());
        skip(destination.getVersion());
        skip(destination.getCreatedAt());
        skip(destination.getUpdatedAt());
        skip(destination.getTotalPrice());
        skip(destination.getSub());
        map(source.getStatus(), destination.getStatus());
        map(source.getQuantity(), destination.getQuantity());
        map(source.getShippingAddress(), destination.getShippingAddress());
        map(source.getBillingAddress(), destination.getBillingAddress());
        map(source.getContactEmail(), destination.getContactEmail());
        map(source.getContactPhone(), destination.getContactPhone());
        map(source.getPaymentMethod(), destination.getPaymentMethod());
        map(source.getPaymentStatus(), destination.getPaymentStatus());
        map(source.getTransactionId(), destination.getTransactionId());
        map(source.getPaidAt(), destination.getPaidAt());
        map(source.getShippedAt(), destination.getShippedAt());
        map(source.getDeliveredAt(), destination.getDeliveredAt());
        map(source.getCancelledAt(), destination.getCancelledAt());
      }
    };
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
    configureModelMapper(modelMapper, productRepository);
    return modelMapper;
  }
}
