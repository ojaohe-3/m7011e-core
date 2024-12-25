package m7011e.the_homeric_odyssey.modelsModule.models.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Product {
    private UUID id;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String description;
    private Double price;
    private Set<String> categories;
    private Set<String> documents;
    private String displayImage;
    private String companyLogo;
    private String contactEmail;
    private String contactPhone;
    private String contactFax;
    private String contactWebsite;
    private String contactAddress;
}
