# 20220817-1
## Особенность задачи: 
1. **В поле одной сущности находится другая сущность.**
   
   Решение - если список сущностей нужен только при чтении другой сущности, то создаем Map<id, Entity> как ниже.
   
   Если нужен далее, то создаем метод на создание List, а потом с помощью `list.stream().collect(Collectors.toMap(Entity::getId, Function.identity()));` создаем Map<id, Entity>
2. **В csv есть поле в виде массива id-шников другой сущности.**

   Решение - передаем строку с id, с Map<id, Entity>, делаем substring строки, сплитим через "," , делаем trim, и применяем getOrDefault

   ```java
   private List<Product> getProductsByIds(String productsString, Map<Integer, Product> productMap) {
        productsString = productsString.substring(1, productsString.length() - 1);
        return Arrays.stream(productsString.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .map(id -> productMap.getOrDefault(id, null))
                .toList();
    }
   ```
3. **По какому-то признаку нужно объединить записи и найти сумму, количество и т.д.**
  
      Решение - группировка по этому общему признаку. Получаем Map<признак, List<Entity>>. Далее в записи результата для каждого элемента мапы делаем действие

      ```java
      public Map<String, List<Order>> groupOrdersByMonth(List<Order> orders) {
        LocalDateTime dateFrom = getDateFrom();
        return orders.stream()
                .filter(order -> order.getDateCreated().isAfter(dateFrom))
                .collect(Collectors.groupingBy(order ->
                        order.getDateCreated().format(DateTimeFormatter.ofPattern("MM.yyyy"))));
        }

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
   ```


Задачи экзамена по Java 17/08/2022 - экзаменуемый 1

Вы работаете над развитием сайта интернет-магазина. Одна из задач включает статистику в личном кабинете администратора. Вам была поставлена задача написать программу подсчета такой статистики и выводить результат в файл.

Данные о работе интернет-магазина хранятся в csv-файлах в папке data. Исходных файла 2:
1) файл products, хранящий данные о товарах магазина;
2) файл orders, хранящий данные о совершенных заказах.

Первая строка файла (она же - заголовок) представляет собой перечень полей, хранящихся в файле, разделенных точкой с запятой (;). Остальные строки - данные. Одна строка – одна сущность (либо продукт, либо заказ). Значения различных полей так же разделены точкой с запятой, а порядок следования значений в строке равен порядку следования полей в заголовке.
При этом предполагается, что ни одна из строк-значений не содержит знака точки с запятой (то есть никакое описание, название товара и другие строковые поля не могут содержать знака «;»).

Заголовки файлов следующие:

products: product_id; name; description; weight; cost
, где 
                - product_id - идентификатор товара, целое число 
                - name - наименование товара, строка 
                - description - подробное описание товара, строка 
                - weight - его вес, положительное число 
                - cost - его цена, целое число

orders: order_id; user_id; date_created; products
, где 
                - order_id - идентификатор заказа, целое число
                - user_id - идентификатор пользователя, выполнившего заказ, целое число
                - date_created - дата совершения заказа, дата в формате "10.08.2022 15:47"
                - products - перечень разделенных запятой идентификаторов товаров (целых чисел) в заказе, взятый в квадратные скобки, строка. Пример: [3,7 , 12, 5]

Подсчитайте общее количество проданных товаров и общую их стоимость за каждый месяц за последний год по июль включительно.
Результат выведите в csv-файл с заголовком, где одна строка отражает данные по одному месяцу. Необходимо вывести для каждого месяца следующую информацию: номер с годом (в формате "07.2022"), количество проданных товаров, общая стоимость проданных товаров.
