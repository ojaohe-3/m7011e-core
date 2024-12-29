package m7011e.the_homeric_odyssey.core.services.validation;

import lombok.NonNull;
import org.springframework.validation.Errors;

public interface VerificationRow<T> {
  void validate(@NonNull T resource, Errors errors);

  default void rejectOnCondition(
          boolean condition, Errors errors, String field, String errorCode) {
    if (condition) {
      errors.rejectValue(field, errorCode);
    }
  }
}
