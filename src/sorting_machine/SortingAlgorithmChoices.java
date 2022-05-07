
package sorting_machine;
/**
 * Options for sorting algorithm to use from sortingFunctionFactory
 */
public enum SortingAlgorithmChoices {
    /**
     * T(n) = O(n^2), Omega(n) <br>
     * Very good when an array is almost sorted!
     */
    INSERTION_SORT,
    /**
     * T(n) = Theta(n^2)
     */
    BUBBLE_SORT,

    /**
     * T(n) = Omega(nlogn), O(n^2)
     * Good time complexity relies on selecting the ideal pivot. <br>
     * If a good pivot is selected, in this package's case it uses median of three, <br>
     * the algorithm will finish in nlogn time <br>
     * This algorithm is very good when your machine has a big cache that relies on locality.
     */
    QUICK_SORT,

    /**
     * T(n) = Theta(n^2)
     */
    SELECTION_SORT
}


