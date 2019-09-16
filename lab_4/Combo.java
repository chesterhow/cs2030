import java.util.List;
import java.util.ArrayList;

public class Combo extends Food {
    private List<Food> itemsInCombo;

    public Combo(int id, String type, String name, int price, List<Food> itemsInCombo) {
        super(id, type, name, price);
        this.itemsInCombo = new ArrayList<>();

        for (Food item : itemsInCombo) {
            this.itemsInCombo.add(item);
        }
    }

    @Override
    public String toString() {
        String output = super.toString();

        for (Food item : this.itemsInCombo) {
            output += "\n   " + item.toString();
        }

        return output;
    }
}
