package m7011e.the_homeric_odyssey.core.services.integration;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m7011e.the_homeric_odyssey.authentication_components.services.SystemUserAuthenticationService;
import m7011e.the_homeric_odyssey.authentication_components.services.UserAuthenticationHelper;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Order;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.Product;
import m7011e.thehomericodyssey.eventlogmodels.api.VendorEventApiRestClient;
import m7011e.thehomericodyssey.eventlogmodels.models.EventType;
import m7011e.thehomericodyssey.eventlogmodels.models.MetaData;
import m7011e.thehomericodyssey.eventlogmodels.models.VendorEvent;
import m7011e.thehomericodyssey.eventlogmodels.utils.MetaDataUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventLogIntegrationService {

  private final UserAuthenticationHelper userAuthenticationHelper;

  private final VendorEventApiRestClient vendorEventApiRestClient;

  private final SystemUserAuthenticationService systemUserAuthenticationService;

  public void logEvent(VendorEvent event) {
    // TODO make circuit breaker pattern
    try {
      log.debug(
          "Sending event to event-log registry. type {}, message {}",
          event.getEventType(),
          event.getEventMessage());
      systemUserAuthenticationService.executeWithSystemToken(
          () -> vendorEventApiRestClient.createEvent(event));
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode().is4xxClientError()) {
        log.error(
            "Request failed status: {}, message: {}. Event: {}",
            e.getStatusCode(),
            e.getResponseBodyAsString(),
            event,
            e);
      } else if (e.getStatusCode().is5xxServerError()) {
        log.error(
            "Request failed, is event-registry down?: {}, message: {}. Event: {}",
            e.getStatusCode(),
            e.getResponseBodyAsString(),
            event,
            e);
      }
      throw e;
    } catch (Exception e) {
      log.error("Error sending to event client", e);
      throw e;
    }
  }

  public void createVendorEventAndSend(
      String metaData, UUID productId, UUID target, EventType type, String message) {
    VendorEvent event =
        VendorEvent.builder()
            .issuerUserId(
                UUID.fromString(
                    userAuthenticationHelper.getUserId().orElse(UUID.randomUUID().toString())))
            .issuerUsername("TODO") // TODO make user info service with auth-registry in commons
            .metaData(
                    new HashSet<>(MetaDataUtils.createMetaDataSetFromValue(metaData)))
            .targetUserId(target)
            .eventType(type)
            .targetProductId(productId)
            .eventMessage(message)
            .build();

    logEvent(event);
  }

  public void sendProductEvent(Product product, EventType eventType, String message) {
    try {
      createVendorEventAndSend(
          MetaDataUtils.javaObjectToString(product),
          product.getId(),
          product.getSub(),
          eventType,
          message);
    } catch (JsonProcessingException e) {
      log.error("Failed to process product {} when sending an event", product.getId(), e);
      throw new IllegalStateException(e);
    }
  }

  public void sendOrderEvent(Order order, EventType eventType, String message) {
    try {
      createVendorEventAndSend(
          MetaDataUtils.javaObjectToString(order),
          order.getProductId(),
          order.getSub(),
          eventType,
          message);
    } catch (JsonProcessingException e) {
      log.error("Failed to process order {} when sending an event", order.getId(), e);
      throw new IllegalStateException(e);
    }
  }
}
