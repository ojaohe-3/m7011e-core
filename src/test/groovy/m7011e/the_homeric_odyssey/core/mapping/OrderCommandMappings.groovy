package m7011e.the_homeric_odyssey.core.mapping

import m7011e.the_homeric_odyssey.core.configuration.ModelMapperConfiguration
import m7011e.the_homeric_odyssey.core.repository.ProductRepository
import m7011e.the_homeric_odyssey.core.utils.TestDataFixture
import m7011e.the_homeric_odyssey.coreorm.orm.ProductDb
import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderCreateCommand
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order
import org.modelmapper.ModelMapper
import spock.lang.Specification

class OrderCommandMappings extends Specification {

    private ModelMapper modelMapper = new ModelMapper();

    private ProductRepository productRepository = Mock()

    def setup() {
        ModelMapperConfiguration.configureModelMapper(modelMapper, productRepository)
    }

    def "command mapping -- createOrderCommand to Order"() {
        given:
        def command = TestDataFixture.createTestCreateOrderCommand()

        when:
        Order order = modelMapper.map(command, Order)

        then:
        1 * productRepository.findById(command.productId) >> Optional.of(getProductDb(command))

        and:
        order.product.id == command.productId
        order.quantity == command.quantity
        order.transactionId == command.transactionId
        order.paymentStatus == command.paymentStatus
        order.deliveredAt == command.deliveredAt
        order.paidAt == command.paidAt
        order.billingAddress == command.billingAddress
        order.paymentMethod == command.paymentMethod
        order.cancelledAt == command.cancelledAt
        order.status == command.status
        order.shippingAddress == command.shippingAddress
        order.contactPhone == command.contactPhone
        order.contactEmail == command.contactEmail
        order.shippedAt == command.shippedAt
    }

    private ProductDb getProductDb(OrderCreateCommand command) {
        return modelMapper.map(TestDataFixture.createTestProduct(id: command.productId), ProductDb)
    }
}