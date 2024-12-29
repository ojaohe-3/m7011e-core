package m7011e.the_homeric_odyssey.core.mapping

import m7011e.the_homeric_odyssey.core.configuration.ModelMapperConfiguration
import m7011e.the_homeric_odyssey.core.repository.ProductRepository
import m7011e.the_homeric_odyssey.core.utils.TestDataFixture
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductCreateCommand
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductUpdateCommand
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product
import org.modelmapper.ModelMapper
import spock.lang.Specification

class ProductCommandMappings extends Specification {

    private ModelMapper modelMapper = new ModelMapper();

    private ProductRepository productRepository = Mock()

    def setup() {
        ModelMapperConfiguration.configureModelMapper(modelMapper, productRepository)
    }

    def "command mapping -- createProductCommand to Product"() {
        given:
        ProductCreateCommand command = TestDataFixture.createTestProductCommand()

        when:
        Product product = modelMapper.map(command, Product)

        then:
        command.name == product.name
        command.description == product.description
        command.price == product.price
        command.status == product.status
        command.categories == product.categories
        command.documents == product.documents
        command.displayImage == product.displayImage
        command.companyLogo == product.companyLogo
        command.contactEmail == product.contactEmail
        command.contactPhone == product.contactPhone
        command.contactFax == product.contactFax
        command.contactWebsite == product.contactWebsite
        command.contactAddress == product.contactAddress
    }

    def "command mapping -- updateProductCommand to Product"() {
        given:
        ProductUpdateCommand command = TestDataFixture.createUpdateTestProductCommand()

        when:
        Product product = modelMapper.map(command, Product)

        then:
        command.name == product.name
        command.description == product.description
        command.price == product.price
        command.status == product.status
        command.categories == product.categories
        command.documents == product.documents
        command.displayImage == product.displayImage
        command.companyLogo == product.companyLogo
        command.contactEmail == product.contactEmail
        command.contactPhone == product.contactPhone
        command.contactFax == product.contactFax
        command.contactWebsite == product.contactWebsite
        command.contactAddress == product.contactAddress
    }
}