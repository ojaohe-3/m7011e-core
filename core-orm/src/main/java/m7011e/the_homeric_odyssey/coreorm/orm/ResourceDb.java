package m7011e.the_homeric_odyssey.coreorm.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "resource")
@Entity
public class ResourceDb extends AbstractDbObject{

  @Column(nullable = false)
  private String value;

  private String resourceId;
}
