package io;

import entity.Order;
import entity.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataLoader {

    private static final String PRODUCTS_FILENAME = "data\\products.csv";
    private static final String ORDERS_FILENAME = "data\\orders.csv";
    private static final String SEPARATOR = ";";

    public List<Order> readOrderData() throws IOException {
        var productMap = readProductData();
        try (Stream<String> lines = Files.lines(Paths.get(ORDERS_FILENAME))) {
            return lines
                    .skip(1)
                    .map(line -> line.split(SEPARATOR))
                    .map(data -> new Order(
                            Integer.parseInt(data[0]),
                            Integer.parseInt(data[1]),
                            data[2],
                            getProductsByIds(data[3], productMap)
                    ))
                    .toList();
        }
    }

    private Map<Integer, Product> readProductData() throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(PRODUCTS_FILENAME))) {
            return lines
                    .skip(1)
                    .map(line -> line.split(SEPARATOR))
                    .map(data -> new Product(
                            Integer.parseInt(data[0]),
                            data[1],
                            data[2],
                            Double.parseDouble(data[3]),
                            Integer.parseInt(data[4])
                    ))
                    .collect(Collectors.toMap(Product::id, Function.identity()));
        }
    }

    private List<Product> getProductsByIds(String productsString, Map<Integer, Product> productMap) {
        productsString = productsString.substring(1, productsString.length() - 1);
        return Arrays.stream(productsString.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .map(id -> productMap.getOrDefault(id, null))
                .toList();
    }

}
