import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Integer> numbers = new ArrayList<>();
    static int prelast = 0;
    static int last = 1;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj pierwszą liczbę (m): ");
        int m = scanner.nextInt();

        System.out.print("Podaj drugą liczbę (n): ");
        int n = scanner.nextInt();

        scanner.close();
        numbers.add(m);
        numbers.add(n);
        System.out.printf("A(%d,%d)", m, n);
        ackerman(numbers.get(prelast), numbers.get(last));

    }

    public static void one() {
        int m = numbers.get(prelast);
        int n = numbers.get(last);
        numbers.set(prelast, numbers.get(prelast)-1);
        numbers.set(last, m);
        numbers.add(n-1);
        prelast++;
        last++;
        String result = formatList(numbers);
        System.out.println("\t= "+result);
        ackerman(numbers.get(prelast-1), ackerman(numbers.get(prelast), numbers.get(last)));
    }

    public static void two() {
        int m = numbers.get(prelast);
        numbers.set(prelast, m-1);
        numbers.set(last, 1);
        String result = formatList(numbers);
        System.out.println("\t= "+result);

        ackerman(numbers.get(prelast), numbers.get(last));
    }

    public static void three() {
        numbers.remove(prelast);
        prelast--;
        last--;
        numbers.set(last, numbers.get(last)+1);
        String result = formatList(numbers);
        System.out.println("\t= "+result);
        //ackerman(numbers.get(prelast), numbers.get(last));
    }
    public static String formatList(List<Integer> list) {
        List<Integer> temp = new ArrayList<>(list);  // Tworzenie nowej listy
        if (temp.size() == 1) {
            return temp.get(0).toString();
        } else {
            int lastElement = temp.remove(0);
            String subList = formatList(temp);
            return "A(" + lastElement + ", " + subList + ")";
        }
    }
    public static int ackerman(int m, int n) {
        if (m == 0) {
            //three(numbers.get(prelast), numbers.get(last));
            three();
        } else if (m > 0 && n == 0) {
            //two(numbers.get(prelast), numbers.get(last));
            two();
        } else if (m > 0 && n > 0) {
            //one(numbers.get(prelast), numbers.get(last));
            one();
        } else {
            throw new RuntimeException("Błąd!"); // Nieprawidłowe wartości m i n
        }
        return m;
    }
}