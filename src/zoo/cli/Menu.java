package zoo.cli;

import java.util.Scanner;

/**
 * This class represents a menu in the program.
 */
public abstract class Menu {

    /**
     * The scanner used to read input from the command line.
     */
    private final static Scanner scanner = new Scanner(System.in);

    /**
     * Reads lines until an integer is given.
     *
     * @return the next int given to stdin.
     */
    protected static int readInt() {
        String line;
        while (true) {
            line = scanner.nextLine();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException ignored) {
                System.out.println("Try again");
            }
        }
    }

    /**
     * @param lower the lower bound of the number
     * @param upper the upper bound of the number
     * @return a number read from stdin, in the range [lower,upper].
     */
    protected static int readInt(int lower, int upper) {
        int toReturn;
        System.out.println("Please enter your option: " + lower + "-" + upper);
        do {
            toReturn = readInt();
            if (toReturn < lower || toReturn > upper)
                System.out.println("Try again");
        }
        while (toReturn < lower || toReturn > upper);
        return toReturn;
    }

    /**
     * @return a new line from stdin.
     */
    protected static String readLine() {
        return scanner.nextLine();
    }

    /**
     * Renders the menu to the console.
     */
    public abstract void render();
}
