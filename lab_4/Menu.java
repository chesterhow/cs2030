import java.util.List;
import java.util.ArrayList;

public class Menu {
    private int itemCount = 0;
    private final List<String> types;
    private final List<List<Food>> itemsByType;
    private final List<Combo> combos;

    public Menu() {
        this.types = new ArrayList<>();
        this.itemsByType = new ArrayList<>();
        this.combos = new ArrayList<>();
    }

    public List<List<Food>> getItemsByType() {
        return this.itemsByType;
    }

    public List<Combo> getCombos() {
        return this.combos;
    }

    // Add food item
    public Food add(String type, String name, int price) {
        Food newFood;

        int typeIndex = this.types.indexOf(type);

        if (typeIndex == -1) {
            this.types.add(type);
            typeIndex = this.types.indexOf(type);
            this.itemsByType.add(new ArrayList<>());
        }

        Food item = new Food(this.itemCount, type, name, price);
        List<Food> itemsOfType = this.itemsByType.get(typeIndex);
        itemsOfType.add(item);
        this.itemCount++;

        return item;
    }

    // Add combo item
    public Food add(String type, String name, List<Integer> ids) {
        List<Food> itemsInCombo = new ArrayList<>();
        int price = 0;

        for (int id : ids) {
            for (List<Food> itemsOfType : this.itemsByType) {
                for (Food item : itemsOfType) {
                    if (item.getId() == id) {
                        itemsInCombo.add(item);
                        price += item.getPrice();
                    }
                }
            }
        }

        Combo combo = new Combo(this.itemCount, type, name, price - 50, itemsInCombo);
        this.combos.add(combo);
        this.itemCount++;
        return combo;
    }

    public void print() {
        for (List<Food> itemsOfType : this.itemsByType) {
            for (Food item : itemsOfType) {
                System.out.println(item);
            }
        }

        for (Combo combo : this.combos) {
            System.out.println(combo);
        }
    }
}
