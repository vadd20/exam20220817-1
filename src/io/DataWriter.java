package io;

import entity.Order;
import entity.Product;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

public class DataWriter {

    private static final String RESULT_FILENAME = "data\\result.txt";
    private static final String SEPARATOR = ", ";

    public void write(Map<String, List<Order>> monthToOrders) throws IOException {
        try (var bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RESULT_FILENAME)))) {
            bw.write("month, total_items, total_cost");
            bw.newLine();

            monthToOrders
                    .forEach((date, orders) -> {

                        long totalItems = orders.stream()
                                .map(Order::getProducts)
                                .count();

                        long totalCost = orders.stream()
                                .flatMap(order -> order.getProducts().stream())
                                .mapToInt(Product::cost)
                                .sum();

                        String result = new StringBuilder()
                                .append(date).append(SEPARATOR)
                                .append(totalItems).append(SEPARATOR)
                                .append(totalCost).toString();

                        try {
                            bw.write(result);
                            bw.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }
}
