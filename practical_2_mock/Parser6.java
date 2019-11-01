import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public Parser shuffle() {
        List<String> lines = this.lines.stream().map(line -> {
            String[] words = line.split(" ");
            String newLine = "";

            for (int i = 0; i < words.length; i++) {
                char[] chars = words[i].toCharArray();
                List<Character> alphabeticCharacters = new ArrayList<>();
                List<Character> otherCharacters = new ArrayList<>();
                List<Integer> otherCharactersIndexes = new ArrayList<>();

                // Split alphabetic characters and other characters
                for (int j = 0; j < chars.length; j++) {
                    if (Character.isAlphabetic(chars[j])) {
                        alphabeticCharacters.add(chars[j]);
                    } else {
                        otherCharacters.add(chars[j]);
                        otherCharactersIndexes.add(j);
                    }
                }

                // Ignore words with <= 3 alphabetic characters
                if (words[i].isBlank()) {
                    newLine += words[i];
                } else if (alphabeticCharacters.size() <= 3) {
                    newLine += " " + words[i];
                } else {
                    // Reinsert other characters to word
                    char secondChar = alphabeticCharacters.remove(1);
                    alphabeticCharacters.add(alphabeticCharacters.size() - 1, secondChar);

                    for (int k = 0; k < otherCharacters.size(); k++) {
                        alphabeticCharacters.add(otherCharactersIndexes.get(k), otherCharacters.get(k));
                    }

                    newLine += " " + alphabeticCharacters.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining());
                }
            }

            return newLine.trim();
        })
        .collect(Collectors.toList());

        return new Parser(lines);
    }

    public Parser chop(String delimitter, int... indexes) {
        List<String> lines = this.lines.stream()
            .map(line -> {
                // Generate portions
                List<String> portions = new ArrayList<>();
                boolean delimitterFound = false;

                while (!line.isEmpty()) {
                    int index = line.indexOf(delimitter);
                    
                    if (index >= 0) {
                        portions.add(line.substring(0, index));
                        line = line.substring(index + delimitter.length());
                        delimitterFound = true;
                    } else {
                        portions.add(line);
                        break;
                    }

                    if (line.isEmpty()) {
                        portions.add("");
                    }
                }

                // Return line if delimitter not found
                if (!delimitterFound) {
                    return line;
                }

                // Filter indexes out of range
                int[] indexList = IntStream.of(indexes)
                    .filter(i -> i <= portions.size())
                    .sorted()
                    .toArray();

                // Recreate chopped line
                String newLine = "";

                for (int i = 0; i < indexList.length; i++) {
                    if (indexList[i] - 1 < portions.size()) {
                        newLine += portions.get(indexList[i] - 1);

                        if (i != indexList.length - 1) {
                            newLine += delimitter;
                        }
                    }
                }

                return newLine;
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
