import java.util.function.BiPredicate;

public class SortingFunctionFactory<T extends Comparable<T>> {
    private BiPredicate<T, T> generateShouldSwap(SortingOrderChoices sortingOrderChoices) {
        return switch (sortingOrderChoices) {
            case ASCENDING -> ((l, r) -> l.compareTo(r) > 0);
            case DESCENDING -> ((l, r) -> l.compareTo(r) < 0);
        };
    }

    private BiPredicate<T, T> generateShouldNotSwap(SortingOrderChoices sortingOrderChoices) {
        return switch (sortingOrderChoices) {
            case ASCENDING -> ((l, r) -> l.compareTo(r) < 0);
            case DESCENDING -> ((l, r) -> l.compareTo(r) > 0);
        };
    }

    private void swap(T[] arr, int l, int r) {
        T temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }

    public SortingFunction<T> generate(SortingAlgorithmChoices algorithmChoice, SortingOrderChoices orderChoice) {
        return switch (algorithmChoice) {
            case BUBBLE_SORT -> generateBubbleSort(generateShouldSwap(orderChoice));
            case SELECTION_SORT -> generateSelectionSort(generateShouldSwap(orderChoice));
            case INSERTION_SORT -> generateInsertionSort(generateShouldSwap(orderChoice));
            case QUICK_SORT -> generateQuickSort(generateShouldSwap(orderChoice), generateShouldNotSwap(orderChoice));
        };
    }

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


    @FunctionalInterface
    interface MedianOfThreeCallback<T extends Comparable<T>> {
        int sort(T[] arr, int first, int last);
    }


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
