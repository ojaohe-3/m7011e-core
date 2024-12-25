package m7011e.the_homeric_odyssey.coreorm.configuration.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Table(name = "products")
@Entity
@Data
@RequiredArgsConstructor
public class ProductDB {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String name;

    @Column(length = 1024)
    private String description;
    private Double price;

    @OneToMany
    private Set<ResourceDb> categories;

    @OneToMany
    private Set<ResourceDb> documents;

    private String displayImage;
    private String companyLogo;
    private String contactEmail;
    private String contactPhone;
    private String contactFax;
    private String contactWebsite;
    private String contactAddress;
}
