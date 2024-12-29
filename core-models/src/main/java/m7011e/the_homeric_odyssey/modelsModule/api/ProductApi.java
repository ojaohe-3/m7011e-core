package m7011e.the_homeric_odyssey.modelsModule.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductCreateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductListCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.comands.ProductUpdateCommand;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Products", description = "Product management API endpoints")
public interface ProductApi {

  @Operation(
      summary = "Create new product",
      description = "Creates a new product with the provided details")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Product successfully created",
        content = @Content(schema = @Schema(implementation = Product.class))),
    @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PostMapping
  ResponseEntity<Product> createProduct(
      @Parameter(description = "Product creation details", required = true) @RequestBody
          ProductCreateCommand command);

  @Operation(
      summary = "Update product",
      description =
          "Updates an existing product with the provided details. Uses optimistic locking with version check.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Product successfully updated",
        content = @Content(schema = @Schema(implementation = Product.class))),
    @ApiResponse(responseCode = "404", description = "Product not found"),
    @ApiResponse(
        responseCode = "409",
        description = "Conflict - product was modified by another transaction")
  })
  @PutMapping("/{id}")
  ResponseEntity<Product> updateProduct(
      @Parameter(description = "Product ID", required = true) @PathVariable UUID id,
      @Parameter(description = "Current version of the product", required = true)
          @RequestHeader(name = "If-Match")
          Long version,
      @Parameter(description = "Product update details", required = true) @RequestBody
          ProductUpdateCommand command);

  @Operation(summary = "Get product", description = "Retrieves a specific product by ID")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Product found",
        content = @Content(schema = @Schema(implementation = Product.class))),
    @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @GetMapping("/{id}")
  ResponseEntity<Product> getProduct(
      @Parameter(description = "Product ID", required = true) @PathVariable UUID id);

  @Operation(
      summary = "List products",
      description = "Retrieves a paginated list of products with optional filtering")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Products retrieved successfully",
        content = @Content(schema = @Schema(implementation = Page.class)))
  })
  @GetMapping
  ResponseEntity<Page<Product>> listProducts(
      @Parameter(description = "Product listing parameters", required = true) @ModelAttribute
          ProductListCommand command);

  @Operation(
      summary = "Delete product",
      description = "Deletes a specific product by ID. Uses optimistic locking with version check.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
    @ApiResponse(responseCode = "404", description = "Product not found"),
    @ApiResponse(
        responseCode = "409",
        description = "Conflict - product was modified by another transaction")
  })
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteProduct(
      @Parameter(description = "Product ID", required = true) @PathVariable UUID id,
      @Parameter(description = "Current version of the product", required = true)
          @RequestHeader(name = "If-Match")
          Long version);
}
