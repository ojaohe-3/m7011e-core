package m7011e.the_homeric_odyssey.coreorm.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "resource")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDb extends AbstractDbObject {

  @Column(nullable = false)
  private String value;

  private String resourceId;
}
