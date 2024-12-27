package m7011e.the_homeric_odyssey.core.services.validation.product;

import lombok.NonNull;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.springframework.validation.Errors;

public class ProductRequiredFieldsValidator implements ProductVerificationRow {
  @Override
  public void validate(@NonNull Product resource, Errors errors) {
    rejectOnCondition(resource.getName() == null, errors, "name", "product.name.required");
    rejectOnCondition(
        resource.getPrice() == null || resource.getPrice() < 0,
        errors,
        "price",
        "product.price.valid.required");
    rejectOnCondition(resource.getStatus() == null, errors, "status", "product.status.required");
  }
}
