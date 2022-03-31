import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) {
        String myWords = "This is a test";
        String myWords2 = "Testing 1,2,3";
        List<String> List1 = Stream.of(myWords,myWords2)
                .collect(Collectors.toList());
        int total = characterSum(List1, 2);
        System.out.println("Number of characters is " + total);
    }

    static int characterSum(final List<String> words, final int threshold) {
         return words.stream()
                .map(word -> word.length())
                .filter(length -> length > threshold)
                .reduce(0, (total,l) -> total+l);
    }
}
