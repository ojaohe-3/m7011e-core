package m7011e.the_homeric_odyssey.core.services.validation;

import java.util.function.Supplier;
import org.springframework.validation.Errors;

public interface VerificationRow<T> {
  void validate(T user, Errors errors);

  default void rejectOnCondition(
      Supplier<Boolean> condition, Errors errors, String field, String errorCode) {
    if (condition.get()) {
      errors.rejectValue(field, errorCode);
    }
  }
}
