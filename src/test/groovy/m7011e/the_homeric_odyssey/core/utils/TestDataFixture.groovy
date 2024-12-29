package m7011e.the_homeric_odyssey.core.utils

import m7011e.the_homeric_odyssey.modelsModule.models.comands.OrderCreateCommand
import m7011e.the_homeric_odyssey.modelsModule.models.domain.*
import org.apache.commons.lang3.RandomStringUtils

import java.time.LocalDateTime
import java.util.stream.Collectors
import java.util.stream.Stream

class TestDataFixture {

    static Product createTestProduct(Map overrides = [:]) {
        def defaultDateTime = LocalDateTime.now()
        def defaultId = UUID.randomUUID()

        def product = Product.builder()
                .id(overrides.id ?: defaultId)
                .version(overrides.version ?: 0)
                .createdAt(overrides.createdAt ?: defaultDateTime)
                .updatedAt(overrides.updatedAt ?: defaultDateTime)
                .name(overrides.name ?: "Test Product ${RandomStringUtils.random(10)}")
                .description(overrides.description ?: "This is a test product description")
                .price(overrides.price ?: 99.99)
                .status(overrides.status ?: ProductStatus.AVAILABLE)
                .categories(overrides.categories ?: createTestResourceBatch(1) as Set)
                .documents(overrides.documents ?: createTestResourceBatch(2) as Set)
                .displayImage(overrides.displayImage ?: "https://example.com/image.jpg")
                .companyLogo(overrides.companyLogo ?: "https://example.com/logo.png")
                .contactEmail(overrides.contactEmail ?: "test@example.com")
                .contactPhone(overrides.contactPhone ?: "+1-555-0123")
                .contactFax(overrides.contactFax ?: "+1-555-0124")
                .contactWebsite(overrides.contactWebsite ?: "https://example.com")
                .contactAddress(overrides.contactAddress ?: "123 Test Street, Test City")
                .sub(overrides.sub ?: UUID.randomUUID())
                .build()

        return product
    }

    static Set<Resource> createTestResourceBatch(int size = 1) {
        return Stream.generate { createTestResource() }.limit(size).collect(Collectors.toSet())
    }

    static Resource createTestResource(Map overrides = [:]) {
        return Resource.builder()
                .id(overrides.id ?: UUID.randomUUID())
                .version(overrides.version ?: 0)
                .createdAt(overrides.createdAt ?: LocalDateTime.now())
                .updatedAt(overrides.updatedAt ?: LocalDateTime.now())
                .value(RandomStringUtils.random(10))
                .resourceId(RandomStringUtils.random(10))
                .build()
    }

    static Order createTestOrder(Map overrides = [:]) {
        def defaultDateTime = LocalDateTime.now()
        def defaultId = UUID.randomUUID()
        def defaultProduct = createTestProduct()

        def defaultOrder = Order.builder()
                .id(overrides.id ?: defaultId)
                .version(overrides.version ?: 0)
                .createdAt(overrides.createdAt ?: defaultDateTime)
                .updatedAt(overrides.updatedAt ?: defaultDateTime)
                .sub(overrides.sub ?: UUID.randomUUID())
                .status(overrides.status ?: OrderStatus.PENDING)
                .product(overrides.product ?: defaultProduct)
                .quantity(overrides.quantity ?: 1)
                .totalPrice(overrides.totalPrice ?: defaultProduct.price)
                .shippingAddress(overrides.shippingAddress ?: "123 Shipping St, Ship City")
                .billingAddress(overrides.billingAddress ?: "123 Billing St, Bill City")
                .contactEmail(overrides.contactEmail ?: "customer@example.com")
                .contactPhone(overrides.contactPhone ?: "+1-555-9876")
                .paymentMethod(overrides.paymentMethod ?: "CREDIT_CARD")
                .paymentStatus(overrides.paymentStatus ?: "PENDING")
                .transactionId(overrides.transactionId ?: "TXN-${UUID.randomUUID().toString().substring(0, 8)}")
                .paidAt(overrides.paidAt ?: defaultDateTime)
                .shippedAt(overrides.shippedAt ?: defaultDateTime)
                .deliveredAt(overrides.deliveredAt ?: defaultDateTime)
                .cancelledAt(overrides.cancelledAt ?: defaultDateTime)
                .build()

        return defaultOrder
    }

    static OrderCreateCommand createTestCreateOrderCommand(Map overrides = [:]) {
        def defaultDateTime = LocalDateTime.now()

        def command = OrderCreateCommand.builder()
                .productId(overrides.productId ?: UUID.randomUUID())
                .status(overrides.status ?: OrderStatus.PENDING)
                .quantity(overrides.quantity ?: 1)
                .shippingAddress(overrides.shippingAddress ?: "123 Shipping St, Ship City")
                .billingAddress(overrides.billingAddress ?: "123 Billing St, Bill City")
                .contactEmail(overrides.contactEmail ?: "customer@example.com")
                .contactPhone(overrides.contactPhone ?: "+1-555-9876")
                .paymentMethod(overrides.paymentMethod ?: "CREDIT_CARD")
                .paymentStatus(overrides.paymentStatus ?: "PENDING")
                .transactionId(overrides.transactionId ?: "TXN-${UUID.randomUUID().toString().substring(0, 8)}")
                .paidAt(overrides.paidAt ?: defaultDateTime)
                .shippedAt(overrides.shippedAt ?: defaultDateTime)
                .deliveredAt(overrides.deliveredAt ?: defaultDateTime)
                .cancelledAt(overrides.cancelledAt ?: defaultDateTime)
                .build()
        return command
    }
}
