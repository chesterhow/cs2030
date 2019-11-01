import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class Parser {
    private List<String> lines;

    private Parser(List lines) {
        this.lines = new ArrayList<String>();

        for (Object line : lines) {
            this.lines.add(String.valueOf(line));
        }
    }

    public static Parser parse(List lines) {
        return new Parser(lines.subList(0, lines.size()));
    }

    public Parser linecount() {
        return new Parser(List.of(this.lines.size()));
    }

    public Parser wordcount() {
        int words = 0;

        for (String line : this.lines) {
            if (!line.isBlank()) {
                words += Stream.of(line.split(" "))
                    .filter(word -> !word.isEmpty())
                    .count();
            }
        }

        return new Parser(List.of(words));
    }

    public Parser grab(String str) {
        List<String> lines = this.lines.stream()
            .filter(line -> line.contains(str))
            .collect(Collectors.toList());

        return new Parser(lines);
    }

    public Parser echo() {
        String result = "";
        
        for (String line : this.lines) {
            if (!line.isBlank()) {
                result += Stream.of(line.split(" "))
                    .filter(word -> !word.isEmpty())
                    .map(word -> word.trim())
                    .reduce("", (first, second) -> first + " " + second);
            }
        }

        return new Parser(List.of(result.trim()));
    }

    public Parser chop(int start, int end) {
        List<String> lines = this.lines.stream()
            .map(line -> {
                int newStart = (start <= 0) ? 0 : start - 1;
                int newEnd = (end >= line.length()) ? line.length() : end;

                if (newStart >= line.length()) {
                    return "";
                }

                return line.substring(newStart, newEnd);
            })
            .collect(Collectors.toList());

        return new Parser(lines);
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
