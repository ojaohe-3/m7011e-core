package m7011e.the_homeric_odyssey.core.mapping

import m7011e.the_homeric_odyssey.core.configuration.ModelMapperConfiguration
import m7011e.the_homeric_odyssey.core.repository.ProductRepository
import m7011e.the_homeric_odyssey.core.utils.TestDataFixture
import m7011e.the_homeric_odyssey.coreorm.orm.OrderDb
import m7011e.the_homeric_odyssey.coreorm.orm.ProductDb
import m7011e.the_homeric_odyssey.coreorm.orm.ResourceDb
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Resource
import org.modelmapper.ModelMapper
import org.springframework.data.util.StreamUtils
import spock.lang.Specification

class ModelMapperSpec extends Specification {

    private ModelMapper modelMapper = new ModelMapper();

    private ProductRepository productRepository = Mock()

    def setup() {
        ModelMapperConfiguration.configureModelMapper(modelMapper, productRepository)
    }

    def "modelmapper -- is valid"() {
        when:
        modelMapper.validate()

        then:
        noExceptionThrown()
    }

    def "modelmapper -- product to productDB"() {
        given:
        Product product = TestDataFixture.createTestProduct()

        when:
        ProductDb result = modelMapper.map(product, ProductDb)

        then:
        result.id == product.id
        result.version == product.version
        result.updatedAt == product.updatedAt
        result.createdAt == product.createdAt
        result.name == product.name
        result.companyLogo == product.companyLogo
        result.contactAddress == product.contactAddress
        result.contactEmail == product.contactEmail
        result.contactPhone == product.contactPhone
        result.contactWebsite == product.contactWebsite
        result.contactFax == product.contactFax
        result.description == product.description
        result.price == product.price
        result.displayImage == product.displayImage
        result.status == product.status
        compareResourceList(result.categories, product.categories)
        compareResourceList(result.documents, product.documents)
    }

    def "modelmapper -- order to orderDb"() {
        given:
        Order order = TestDataFixture.createTestOrder()

        when:
        OrderDb result = modelMapper.map(order, OrderDb)

        then:
        result.id == order.id
        result.version == order.version
        result.updatedAt == order.updatedAt
        result.createdAt == order.createdAt
        result.status == order.status
        result.totalPrice == order.totalPrice
        result.shippingAddress == order.shippingAddress
        result.contactEmail == order.contactEmail
        result.contactPhone == order.contactPhone
        result.cancelledAt == order.cancelledAt
        result.billingAddress == order.billingAddress
        result.paidAt == order.paidAt
        result.deliveredAt == order.deliveredAt
        result.product.id == order.product.id
        result.paymentStatus == order.paymentStatus
        result.paymentMethod == order.paymentMethod
        result.transactionId == order.transactionId
        result.quantity == order.quantity
    }


    private static List<Object> compareResourceList(Set<ResourceDb> resourceDbs, Set<Resource> resources) {
        StreamUtils.zip(
                resourceDbs.stream(),
                resources.stream(),
                (resourceDb, resource) -> {
                    compareResource(resource, resourceDb)
                    return null // bifunction requires a result
                }
        ).toList()
    }

    private static void compareResource(Resource resource, ResourceDb resourceDb) {
        assert resourceDb.id == resource.id
        assert resourceDb.version == resource.version
        assert resourceDb.createdAt == resource.createdAt
        assert resourceDb.updatedAt == resource.updatedAt
        assert resourceDb.value == resource.value
        assert resourceDb.resourceId == resource.resourceId
    }
}