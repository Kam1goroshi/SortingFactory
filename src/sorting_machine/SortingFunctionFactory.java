

package sorting_machine;

import java.util.function.BiPredicate;

/**
 * Defines and generates sorting functions
 */
public class SortingFunctionFactory<T extends Comparable<T>> {

    /**
     * Generates a comparison function for the given order choice <br>
     *
     * @param sortingOrderChoice enum for sorting order, i.e. ASCENDING,
     * @return (l, r)->{l>r} when ASCENDING, (l,r)->{l<r} when DESCENDING
     */
    private BiPredicate<T, T> generateShouldSwap(SortingOrderChoices sortingOrderChoice) {
        return switch (sortingOrderChoice) {
            case ASCENDING -> ((l, r) -> l.compareTo(r) > 0);
            case DESCENDING -> ((l, r) -> l.compareTo(r) < 0);
        };
    }

    /**
     * Generates a comparison function for the given order choice
     *
     * @param sortingOrderChoice enum for sorting order, i.e. ASCENDING,
     * @return (l, r)->{l>r} when DESCENDING, (l,r)->{l<r} when ASCENDING
     */
    private BiPredicate<T, T> generateShouldNotSwap(SortingOrderChoices sortingOrderChoice) {
        return switch (sortingOrderChoice) {
            case ASCENDING -> ((l, r) -> l.compareTo(r) < 0);
            case DESCENDING -> ((l, r) -> l.compareTo(r) > 0);
        };
    }

    /**
     * Swaps arr[l] with arr[r]
     *
     * @param arr an array of T
     * @param l   left index
     * @param r   right index
     */
    private void swap(T[] arr, int l, int r) {
        T temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }

    /**
     * Generates a SortingFunction based on given choices
     *
     * @see SortingFunction
     * @param algorithmChoice chosen algorithm
     * @param orderChoice     chosen order
     * @return a sorting function
     */
    public SortingFunction<T> generate(SortingAlgorithmChoices algorithmChoice, SortingOrderChoices orderChoice) {
        return switch (algorithmChoice) {
            case BUBBLE_SORT -> generateBubbleSort(generateShouldSwap(orderChoice));
            case SELECTION_SORT -> generateSelectionSort(generateShouldSwap(orderChoice));
            case INSERTION_SORT -> generateInsertionSort(generateShouldSwap(orderChoice));
            case QUICK_SORT -> generateQuickSort(generateShouldSwap(orderChoice), generateShouldNotSwap(orderChoice));
        };
    }


    /**
     * Generates a function that sorts the given array by mutating
     *
     * @param shouldSwap a function to decide if its two arguments are in sorted order
     * @return An out-function which sorts an array of type T using the bubble-sort algorithm <br>
     */
    private SortingFunction<T> generateBubbleSort(BiPredicate<T, T> shouldSwap) {
        //Implement and return sorting function
        return (arr) -> {
            long steps = 0;
            boolean isSorted;
            do {
                steps++;
                isSorted = true;
                for (int i = 0; i < arr.length - 1; i++) {
                    steps++;
                    if (shouldSwap.test(arr[i], arr[i + 1])) {
                        System.out.println("l:" + arr[i] + ",r:" + arr[i + 1]);
                        swap(arr, i, i + 1);
                        isSorted = false;
                    }
                }
            } while (!isSorted);
            return steps;
        };
    }

    /**
     * Generates a function that sorts the given array by mutating
     *
     * @param shouldSwap a function to decide if its' two arguments are in sorted order
     * @return An out-function which sorts an array of type T using the insertion-sort algorithm <br>
     */
    private SortingFunction<T> generateInsertionSort(BiPredicate<T, T> shouldSwap) {
        //Implement and return sorting function
        return (arr) -> {
            long steps = 0;
            int i, j;
            T temp;
            for (i = 1; i < arr.length; i++) {
                steps++;
                j = i - 1;
                temp = arr[i];
                while (j >= 0 && shouldSwap.test(arr[j], temp)) {
                    steps++;
                    arr[j + 1] = arr[j];
                    j--;
                }
                arr[j + 1] = temp;
            }
            return steps;
        };
    }

    /**
     * Generates a function that sorts the given array by mutating
     *
     * @param shouldSwap a function to decide if its' two arguments are in sorted order
     * @return An out-function which sorts an array of type T using the selection-sort algorithm <br>
     */
    private SortingFunction<T> generateSelectionSort(BiPredicate<T, T> shouldSwap) {
        //Implement and return sorting function
        return (arr) -> {
            long steps = 0;
            for (int i = 0; i < arr.length - 1; i++) {
                steps++;
                for (int j = i + 1; j < arr.length; j++) {
                    steps++;
                    if (shouldSwap.test(arr[i], arr[j])) {
                        swap(arr, i, j);
                    }
                }
            }
            return steps;
        };
    }

    /**
     * Generates a function that sorts the given array by mutating
     *
     * @param shouldSwap a function to decide if its' two arguments are in sorted order
     * @return An out-function which sorts an array of type T using the quick-sort algorithm <br>
     */
    private SortingFunction<T> generateQuickSort(BiPredicate<T, T> shouldSwap, BiPredicate<T, T> shouldNotSwap) {
        class QuickSortCallback {
            /**
             * Sorts the leftmost, middle, and rightmost elements in the sorting range, then returns the middle one <br>
             * This is a good way to find the median, as it greatly increases the chance to keep the steps required <br>
             * in c * nlogn time
             *
             * @param arr The array that is being sorted
             * @param first The first index the current quicksort call works at
             * @param last The last index the current quicksort call works at
             * @param shouldSwap comparison function that decides when elements should be swapped. <br>
             *                   Not really necessary, but it doesn't matter if it's used.
             * @return the pivot index
             */
            private int medianOfThree(T[] arr, int first, int last, BiPredicate<T, T> shouldSwap) {
                int mid = (first + last) / 2;
                if (last - first + 1 < 3)
                    return mid;
                if (shouldSwap.test(arr[first], arr[mid]))
                    swap(arr, first, mid);
                if (shouldSwap.test(arr[mid], arr[last]))
                    swap(arr, mid, last);
                if (shouldSwap.test(arr[first], arr[mid]))
                    swap(arr, first, mid);

                return mid;
            }

            /**
             * Sorts the given array by mutating
             *
             * @param arr the array to be sorted
             * @param first the starting index to sort from, included
             * @param last the last index to sort until, included
             * @param shouldSwap conditional function that decides if there should be a swap
             * @param shouldNotSwap conditional function that decides if there should not be a swap
             * @return how many steps of loops and recursions were required
             */
            private long quickSort(T[] arr, int first, int last, BiPredicate<T, T> shouldSwap,
                                   BiPredicate<T, T> shouldNotSwap) {
                if (last <= first)
                    return 1;
                long steps = 1;
                int l = first, r = last - 1;
                //Find the pivot using the median of 3 method, and push it at the end
                int pivot = medianOfThree(arr, first, last, shouldNotSwap);
                swap(arr, pivot, last);

                while (l < r) {
                    steps++;
                    while (l < last && !shouldSwap.test(arr[l], arr[last])) { //on equal included
                        l++;
                        steps++;
                    }
                    while (r >= first && shouldNotSwap.test(arr[last], arr[r])) { //on equal excluded
                        r--;
                        steps++;
                    }
                    if (l < r)
                        swap(arr, l, r);
                }
                if (shouldSwap.test(arr[l], arr[last]))
                    swap(arr, l, last);

                return steps
                        + quickSort(arr, first, l - 1, shouldSwap, shouldNotSwap)
                        + quickSort(arr, l + 1, last, shouldSwap, shouldNotSwap);
            }
        }

        QuickSortCallback quickSortCallback = new QuickSortCallback();
        //return sorting function
        return (arr) -> quickSortCallback.quickSort(arr, 0, arr.length - 1, shouldSwap, shouldNotSwap);
    }

    /**
     * Interface for a sorting function
     *
     * @param <T> the type of items to be sorted
     */
    @FunctionalInterface
    public interface SortingFunction<T> {
        /**
         * Sorts a given array by mutating it
         *
         * @param array to be sorted
         * @return the steps needed to complete
         */
        long sort(T[] array);
    }
}
