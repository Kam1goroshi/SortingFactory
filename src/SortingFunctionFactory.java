import java.util.function.BiPredicate;

/**
 * Generator of sorting functions
 * Use by instantiating and invoking the generate() method
 *
 * @param <T> the type of the items to be sorted, must implement comparable
 */
public class SortingFunctionFactory<T extends Comparable<T>> {
    /**
     * Generates a comparison function for the given order choice <br>
     * @param sortingOrderChoice enum for sorting order, i.e. ASCENDING,
     * @return (l,r)->{l>r} when ASCENDING, (l,r)->{l<r} when DESCENDING
     */
    private BiPredicate<T, T> generateShouldSwap(SortingOrderChoices sortingOrderChoice) {
        return switch (sortingOrderChoice) {
            case ASCENDING -> ((l, r) -> l.compareTo(r) > 0);
            case DESCENDING -> ((l, r) -> l.compareTo(r) < 0);
        };
    }

    /**
     * Generates a comparison function for the given order choice
     * @param sortingOrderChoice enum for sorting order, i.e. ASCENDING,
     * @return (l,r)->{l>r} when DESCENDING, (l,r)->{l<r} when ASCENDING
     */
    private BiPredicate<T, T> generateShouldNotSwap(SortingOrderChoices sortingOrderChoice) {
        return switch (sortingOrderChoice) {
            case ASCENDING -> ((l, r) -> l.compareTo(r) < 0);
            case DESCENDING -> ((l, r) -> l.compareTo(r) > 0);
        };
    }

    /**
     * Swaps arr[l] with arr[r]
     * @param arr an array of T
     * @param l left index
     * @param r right index
     */
    private void swap(T[] arr, int l, int r) {
        T temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }

    /**
     * @param algorithmChoice chosen algorithm from enum, i.e. BUBBLE_SORT
     * @param orderChoice chosen order from enum, i.e. ASCENDING
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
     * @param shouldSwap a function to decide if its' two arguments are in sorted order
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
     * @param shouldSwap a function to decide if its' two arguments are in sorted order
     * @return An out-function which sorts an array of type T using the quick-sort algorithm <br>
     */
    private SortingFunction<T> generateQuickSort(BiPredicate<T, T> shouldSwap, BiPredicate<T, T> shouldNotSwap) {
        class QuickSortCallback{
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

            private long quickSortHelper(T[] arr, int first, int last, BiPredicate<T, T> shouldSwap,
                                         BiPredicate<T, T> shouldNotSwap) {
                if (last <= first)
                    return 1;
                long steps = 1;
                int l = first, r = last - 1;
                //Find the pivot using the median of 3 method, and push it at the end
                int pivot = medianOfThree(arr, first, last, shouldNotSwap);
                swap(arr, pivot, last);

                while (l < r) {
                    while (l < last && !shouldSwap.test(arr[l], arr[last])) { //on equal included
                        l++;
                        System.out.println(l);
                    }
                    while (r >= first && shouldNotSwap.test(arr[last], arr[r])) { //on equal excluded
                        r--;
                    }
                    if (l < r)
                        swap(arr, l, r);
                }
                swap(arr, l, last);
                return steps
                        + quickSortHelper(arr, first, l - 1, shouldSwap, shouldNotSwap)
                        + quickSortHelper(arr, l + 1, last, shouldSwap, shouldNotSwap);
            }
        }

        QuickSortCallback quickSortCallback = new QuickSortCallback();
        //return sorting function
        return (arr) -> quickSortCallback.quickSortHelper(arr, 0, arr.length - 1, shouldSwap, shouldNotSwap);
    }
}
