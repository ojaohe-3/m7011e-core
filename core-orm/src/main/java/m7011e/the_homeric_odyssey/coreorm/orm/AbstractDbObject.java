package m7011e.the_homeric_odyssey.coreorm.orm;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Data
@RequiredArgsConstructor
public class AbstractDbObject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Version
    private Long version;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
