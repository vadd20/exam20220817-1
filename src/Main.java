import io.DataLoader;
import io.DataWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        DataLoader dataLoader = new DataLoader();
        var orders = dataLoader.readOrderData();

        OrderProcessor processor = new OrderProcessor();
        var monthToOrders = processor.groupOrdersByMonth(orders);

        DataWriter dataWriter = new DataWriter();
        dataWriter.write(monthToOrders);
    }
}