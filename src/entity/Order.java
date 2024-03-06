package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private LocalDateTime dateCreated;
    List<Product> products;

    public Order(int id, int userId, String dateCreated, List<Product> products) {
        this.id = id;
        this.userId = userId;
        this.dateCreated = LocalDateTime.parse(dateCreated, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        this.products = products;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public List<Product> getProducts() {
        return products;
    }
}
