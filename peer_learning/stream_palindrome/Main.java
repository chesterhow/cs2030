import java.util.stream.Stream;
import java.util.stream.IntStream;

public class Main {
    public static boolean isPalindrome(String str) {
        return str.equals((Stream.of(str.split(""))
            .reduce("", (x,y) -> y + x)));
    }

    public static int countPalindrome(String[] strs) {
        return (int)Stream.of(strs)
            .flatMap(str1 -> Stream.of(strs).map(str2 -> str1 + str2))
            .distinct()
            .filter(str -> isPalindrome(str))
            .count();
    }

    public static boolean formPalindrome(String str) {
        String[] chars = str.split("");
        
        String removePairs = Stream.of(chars)
            .reduce("", (s, c) -> {
                if (s.contains(c)) {
                    return s.replace(c, "");
                } else {
                    return s + c;
                }
            });

        return removePairs.length() == 1 || removePairs.length() == 0;
    }

    public static String shortestPalindrome(String str) {
        int id = IntStream.range(0, str.length())
            .dropWhile(i -> !isPalindrome(str.substring(i)))
            .findFirst()
            .orElse(str.length() - 1);
        
        String toAppend = Stream.of(str.substring(0, id).split(""))
            .reduce("", (x,y) -> y + x);

        return str + toAppend;
    }

    public static void main(String[] args) {        
    }
}
