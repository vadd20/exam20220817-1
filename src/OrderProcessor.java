import entity.Order;
import entity.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderProcessor {

    public Map<String, List<Order>> groupOrdersByMonth(List<Order> orders) {
        LocalDateTime dateFrom = getDateFrom();
        return orders.stream()
                .filter(order -> order.getDateCreated().isAfter(dateFrom))
                .collect(Collectors.groupingBy(order ->
                        order.getDateCreated().format(DateTimeFormatter.ofPattern("MM.yyyy"))));
    }

    private LocalDateTime getDateFrom() {
        var now = LocalDateTime.of(2022, 6, 17, 10, 0, 0);

        if (now.getMonth().getValue() > 7) {
            return now.withMonth(7).withDayOfMonth(1);
        } else {
            return now.withYear(now.getYear() - 1).withMonth(7).withDayOfMonth(1);
        }
    }
}
