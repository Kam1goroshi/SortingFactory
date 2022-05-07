/**
 * @author Georgios Pappas
 * @version 1.0
 * @contact g.pappas.1@nup.ac.cy until 2025
 * @licence none
 *
 */
import sorting_machine.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    /**
     * Gets the choice of the user when selecting algorithm or sorting function
     *
     * @param maxInput used to prompt the user to give another input
     * @param scanner  where to find the user's input
     * @return the input as a number
     */
    private static int getInput(int maxInput, Scanner scanner) {
        int input = 0;
        try {
            while ((input = Integer.parseInt(scanner.nextLine())) > maxInput || input < 0) {
                System.out.println(("Incorrect input, please try again:"));
            }
        } catch (Exception e) {
            System.out.println("\nSomething went wrong while reading the user input");
            if (e.getClass() == NumberFormatException.class)
                System.out.println("Perhaps the input was not a number?");
            System.out.println("\nPlease restart the program and try again");
            scanner.close();
            System.exit(0);
        }
        return input;
    }

    /**
     * Generates 1000 random numbers [0,999)
     * <p>
     * Prompts the user to select a sorting algorithm and sorting order
     * <p>
     * Prints the sorted array and the steps that were taken, excluding constant steps outside the loops.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        //Setup a pseudo-random number generator, and use it to fill numbers array with ints in [0,1000)
        Random rng = new Random();
        int n = 1000;
        Integer[] numbers = new Integer[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = rng.nextInt(n);
        }

        //Print the numbers
        System.out.println("The program has generated the following array of pseudo-random integers:");
        System.out.println(Arrays.toString(numbers));

        //Prompt the user to select a sorting algorithm
        System.out.println("\nSelect algorithm:");
        System.out.println("insertion-sort[" + SortingAlgorithmChoices.INSERTION_SORT.ordinal() + "],"
                + " bubble-sort[" + SortingAlgorithmChoices.BUBBLE_SORT.ordinal() + "],"
                + " quick-sort[" + SortingAlgorithmChoices.QUICK_SORT.ordinal() + "],"
                + " selection-sort[" + SortingAlgorithmChoices.SELECTION_SORT.ordinal() + "]");

        //Get input and make a soft check for correctness
        //The program will shut down upon exception, but will prompt for another input if out of choices bounds
        Scanner scanner = new Scanner(System.in);
        int maxInput = SortingAlgorithmChoices.values().length - 1;
        int input = getInput(maxInput, scanner);

        //Save the choice if the input was correct
        SortingAlgorithmChoices sortingAlgorithmChoice = SortingAlgorithmChoices.values()[input];

        //Repeat the above process for sorting algorithm choice input
        System.out.println("\nSelect sorting order:");
        System.out.println("ascending[" + SortingOrderChoices.ASCENDING.ordinal() + "],"
                + " descending[" + SortingOrderChoices.DESCENDING.ordinal() + "]");

        maxInput = SortingOrderChoices.values().length - 1;
        input = getInput(maxInput, scanner);
        SortingOrderChoices sortingOrderChoicesChoice = SortingOrderChoices.values()[input];

        //Setup sorting machine
        SortingMachine<Integer> sortingMachine = new SortingMachine<>(sortingAlgorithmChoice, sortingOrderChoicesChoice);
        //Sort the numbers
        long steps = sortingMachine.sort(numbers);

        System.out.println("After sort:");
        System.out.println(Arrays.toString(numbers));

        String algorithmUsed = switch (sortingMachine.getAlgorithmChoice()){
            case BUBBLE_SORT -> "bubble-sort";
            case SELECTION_SORT -> "selection-sort";
            case INSERTION_SORT -> "insertion-sort";
            case QUICK_SORT -> "quick-sort";
        };

        String orderUsed = switch (sortingMachine.getOrderChoice()){
            case ASCENDING -> "ascending";
            case DESCENDING -> "descending";
        };

        System.out.println("\nAlgorithm: " + algorithmUsed);
        System.out.println("Order: " + orderUsed);
        System.out.println("Steps: " + steps);
        scanner.close();
    }
}
