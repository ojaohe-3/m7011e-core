package m7011e.the_homeric_odyssey.core.services.validation.product;

import lombok.NonNull;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.validation.Errors;

public class ProductContactDetailsValidator implements ProductVerificationRow {
  @Override
  public void validate(@NonNull Product resource, Errors errors) {
    if (resource.getContactEmail() != null) {
      rejectOnCondition(
          !EmailValidator.getInstance().isValid(resource.getContactEmail()),
          errors,
          "contactEmail",
          "product.email.invalid");
    }

    if (resource.getContactPhone() != null) {
      rejectOnCondition(
          !resource.getContactPhone().matches("^\\+?[0-9. -]{10,}$"),
          errors,
          "contactPhone",
          "product.phone.invalid");
    }

    // TODO rest of contact validation.
  }
}
