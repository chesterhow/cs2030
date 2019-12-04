import java.util.stream.Stream;
import java.util.stream.IntStream;

public class StringManipulation {
    private String str;

    private StringManipulation(String str) {
        this.str = str;
    }

    public static StringManipulation of(String str) {
        return new StringManipulation(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof StringManipulation) {
            StringManipulation sm = (StringManipulation)obj;
            return sm.str.equals(this.str);
        }

        return false;
    }

    public IntStream toIntStream() {
        return IntStream
            .range(0, this.str.length())
            .map(i -> (int)this.str.charAt(i));
    }

    public Stream<String> toCharStream() {
        return Stream.of(this.str.split(""));
    }

    public StringManipulation removeAll(char c) {
        String newStr = toCharStream()
            .filter(x -> !(x.charAt(0) == c))
            .reduce("", (x,y) -> x + y);

        return new StringManipulation(newStr);
    }

    public boolean contains(char c) {
        return toCharStream()
            .anyMatch(x -> Character.toLowerCase(x.charAt(0)) == Character.toLowerCase(c));
    }

    public StringManipulation replace(char c1, char c2) {
        String newStr = toCharStream()
            .map(x -> x.charAt(0) == c1 ? String.valueOf(c2) : x)
            .reduce("", (x,y) -> x + y);

        return new StringManipulation(newStr);
    }

    public StringManipulation toLowerCase(char c) {
        return replace(Character.toUpperCase(c), Character.toLowerCase(c));
    }

    public StringManipulation toUpperCase(char c) {
        return replace(Character.toLowerCase(c), Character.toUpperCase(c));
    }

    @Override
    public String toString() {
        return this.str;
    }
}
