public class Main {
    public static void main(String[] args) {
        Double a = (-15.0);
        Double b = 25.0;
        System.out.print(a);
        System.out.print(b);
        System.out.print("\n");
        if (a >= b) {
            System.out.print("a != b");
            System.out.print("qwerty");
        } else {
            System.out.print("a == b");
            System.out.print("xxxxxx");
        }
        System.out.print("\n");
        Double counter = (2.0 + (2.8 * 5.0));
        while (counter <= 25.0) {
            if (counter == 23.0) {
                System.out.print("counter = 23");
                break;
            }
            if (counter == 21.0) {
                counter = (counter + 1.0);
                System.out.print("counter = counter + 1");
                continue;
            }
            counter = (counter + 1.0);
        }
        System.out.print("\n");
        Double j = 0.0;
        do {
            if (j == 2.0) {
                break;
            }
            System.out.print("j = ");
            System.out.print(j);
            j = (j + 1.0);
        } while (j != 5.0);
        System.out.print("\n");
        for (double i = 0.0; (i < 10.0); i = (i + 1.0)) {
            if (i == 6.0) {
                break;
            }
            if (i == 4.0) {
                i = (i + 1.0);
                continue;
            } else {
                System.out.print("i = ");
                System.out.print(i);
            }
        }
        increment(a, b);
        Double x = 10.0;
        Double y = 2.0;
        System.out.print("\n");
        System.out.print("Call one more user method");
        cycleMultiplicative(x, y);
        System.out.print(x);
    }

    private static void increment(Double variable, Double incrementValue) {
        variable = (variable + incrementValue);
        System.out.print("User function example");
        System.out.print(variable);
    }

    private static void cycleMultiplicative(Double firstVariable, Double secondVariable) {
        Double localCounter = 0.0;
        while (localCounter != 6.0) {
            firstVariable = (firstVariable * secondVariable);
            localCounter = (localCounter + 1.0);
        }
        System.out.print(firstVariable);
    }
}