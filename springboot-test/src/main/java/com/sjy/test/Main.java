import java.util.ArrayList;
import java.util.HashSet;

public class Main {


    public static void main(String[] args) {
        HashSet<String> strings = new HashSet<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("14");
        strings.add("16");
        HashSet<String> strings1 = new HashSet<>(strings.size());
        for (String s : strings) {
            if (s.contains("1")) {
                strings1.add(s);
            }
        }
        int size = strings1.size();
        System.out.println(size);
        for (String s : strings1) {
            System.out.println(s);
        }
    }
}
