package m7011e.the_homeric_odyssey.modelsModule.models.comands;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import m7011e.the_homeric_odyssey.modelsModule.models.domain.OrderStatus;
import org.springframework.data.domain.Sort;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderListCommand {
private Integer page = 0;
private Integer size = 10;
private String sortBy;
private Sort.Direction sortDirection = Sort.Direction.ASC;
private OrderStatus statusFilter;
private LocalDateTime startDate;
private LocalDateTime endDate;
private UUID sub;
}
