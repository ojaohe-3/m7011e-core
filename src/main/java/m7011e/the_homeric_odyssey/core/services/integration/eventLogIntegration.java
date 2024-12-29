package m7011e.the_homeric_odyssey.core.services.integration;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.thehomericodyssey.eventlogmodels.api.VendorEventApiRestClient;
import m7011e.thehomericodyssey.eventlogmodels.models.EventType;
import m7011e.thehomericodyssey.eventlogmodels.models.MetaData;
import m7011e.thehomericodyssey.eventlogmodels.models.VendorEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class eventLogIntegration {

  private final UserAuthenticationHelper userAuthenticationHelper;

  private final VendorEventApiRestClient vendorEventApiRestClient;

  public void logEvent(VendorEvent event) {
    // TODO make circuit breaker pattern
    vendorEventApiRestClient.createEvent(event);
  }

  public VendorEvent createVendorEvent(
      MetaData metaData, UUID productId, UUID target, EventType type, String message) {
    return VendorEvent.builder()
        .issuerUserId(
            UUID.fromString(
                userAuthenticationHelper.getUserId().orElse(UUID.randomUUID().toString())))
        .issuerUsername("TODO") // TODO make user info service with auth-registry in commons
        .metaData(metaData)
        .targetUserId(target)
        .eventType(type)
        .targetProductId(productId)
        .eventMessage(message)
        .build();
  }
}
