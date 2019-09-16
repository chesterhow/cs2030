import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();

        while (sc.next().equals("add")) {
            String type = sc.next();

            if (type.equals("Combo")) {
                String name = sc.next();
                List<Integer> ids = new ArrayList<>();

                while (sc.hasNextInt()) {
                    ids.add(sc.nextInt());
                }

                menu.add(type, name, ids);
            } else {
                menu.add(type, sc.next(), sc.nextInt());
            }
        }
        
        menu.print();
        List<Integer> orderIds = new ArrayList<>();

        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                orderIds.add(sc.nextInt());
            } else {
                break;
            }
        }

        int[] ids = new int[orderIds.size()];
        for (int i = 0; i < orderIds.size(); i++) {
            ids[i] = orderIds.get(i);
        }
        System.out.println("\n--- Order ---");
        Order order = new Order(menu).add(ids);
        System.out.println(order);
    }
}
