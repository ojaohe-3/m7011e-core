package m7011e.the_homeric_odyssey.core.util;

import java.util.Objects;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import m7011e.the_homeric_odyssey.coreorm.orm.AbstractDbObject;
import org.hibernate.dialect.lock.OptimisticEntityLockException;

@UtilityClass
public class VersionUtil {

  public void validateVersion(@NonNull Long version, @NonNull AbstractDbObject original)
      throws OptimisticEntityLockException {
    if (!Objects.equals(version, original.getVersion())) {
      throw new OptimisticEntityLockException(original, "invalid version");
    }
  }
}
