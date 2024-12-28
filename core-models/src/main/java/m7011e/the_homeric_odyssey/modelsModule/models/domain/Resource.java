package m7011e.the_homeric_odyssey.modelsModule.models.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Resource {
  private UUID id;
  private Long version;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  @NonNull private String value;
  private String resourceId;
}
