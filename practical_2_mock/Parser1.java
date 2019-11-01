import java.util.List;

public class Parser {
    private List lines;

    private Parser(List lines) {
        this.lines = lines;
    }

    public static Parser parse(List lines) {
        return new Parser(lines.subList(0, lines.size()));
    }

    @Override
    public String toString() {
        String output = "";

        for (int i = 0; i < this.lines.size(); i++) {
            output += this.lines.get(i);
            
            if (i < this.lines.size() - 1) {
                output += "\n";
            }
        }

        return output;
    }
}
