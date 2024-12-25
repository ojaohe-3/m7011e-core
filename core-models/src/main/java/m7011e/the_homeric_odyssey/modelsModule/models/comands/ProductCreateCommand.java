package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import java.util.Set;
import java.util.UUID;

public record ProductCreateCommand(
    String name,
    String description,
    Double price,
    Set<UUID> categoryIds,
    Set<UUID> documentIds,
    String displayImage,
    String companyLogo,
    String contactEmail,
    String contactPhone,
    String contactFax,
    String contactWebsite,
    String contactAddress) {}
