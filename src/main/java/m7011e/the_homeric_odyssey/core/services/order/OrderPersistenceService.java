package m7011e.the_homeric_odyssey.core.services.order;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.core.repository.OrderQueryRepository;
import m7011e.the_homeric_odyssey.core.repository.OrderRepository;
import m7011e.the_homeric_odyssey.core.services.IPersistenceService;
import m7011e.the_homeric_odyssey.core.util.VersionUtil;
import m7011e.the_homeric_odyssey.coreorm.orm.OrderDB;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.resource_server.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPersistenceService implements IPersistenceService<OrderDB, Order> {

  private final ModelMapper modelMapper;

  private final OrderRepository orderRepository;

  private final OrderQueryRepository orderQueryRepository;

  @Override
  public Order create(Order entity) {
    return mapToDomain(orderRepository.saveAndFlush(mapToDB(entity)));
  }

  @Override
  public Order update(Order entity, UUID id, Long version) {
    VersionUtil.validateVersion(
        version, orderRepository.findById(id).orElseThrow(ResourceNotFoundException::new));

    return create(entity);
  }

  @Override
  public void delete(UUID id, Long version) {
    VersionUtil.validateVersion(
        version, orderRepository.findById(id).orElseThrow(ResourceNotFoundException::new));

    orderRepository.deleteById(id);
  }

  @Override
  public Order get(UUID id) {
    return orderRepository
        .findById(id)
        .map(this::mapToDomain)
        .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public List<Order> getAll() {
    return orderRepository.findAll().stream().map(this::mapToDomain).toList();
  }

  @Override
  public Order mapToDomain(OrderDB entity) {
    return modelMapper.map(entity, Order.class);
  }

  @Override
  public OrderDB mapToDB(Order order) {
    return modelMapper.map(order, OrderDB.class);
  }

  public Page<Order> query(OrderListCommand command) {
    Pageable page =
        PageRequest.of(
            command.getPage(),
            command.getSize(),
            Sort.by(
                command.getSortDirection(),
                command.getSortBy() == null ? "createdAt" : command.getSortBy()));

    Specification<OrderDB> specification = orderRepository.createFilterSpecification(command);
    return orderQueryRepository.findAll(specification, page).map(this::mapToDomain);
  }
}
