import java.util.List;
import java.util.ArrayList;

public class Order {
    private final Menu menu;
    private List<Food> itemsOrdered;
    private int bill;

    public Order(Menu menu) {
        this.menu = menu;
        this.itemsOrdered = new ArrayList<>();
        this.bill = 0;
    }
    
    public Order add(int[] ids) {
        for (int id : ids) {
            boolean found = false;

            List<List<Food>> itemsByType = this.menu.getItemsByType();

            for (List<Food> itemsOfType : itemsByType) {
                for (Food item : itemsOfType) {
                    if (item.getId() == id) {
                        this.itemsOrdered.add(item);
                        this.bill += item.getPrice();
                        found = true;
                    }
                }
            }

            if (!found) {
                for (Combo combo : this.menu.getCombos()) {
                    if (combo.getId() == id) {
                        this.itemsOrdered.add(combo);
                        this.bill += combo.getPrice();
                    }
                }
            }
        }

        return this;
    }

    @Override
    public String toString() {
        String output = "\n";
        
        for (Food item : this.itemsOrdered) {
            output += item.toString() + "\n";
        }
        
        output += "Total: " + this.bill;
        
        return output;
    }
}
