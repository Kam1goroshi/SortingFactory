package sorting_machine;

/**
 * The order in which SortingFunctionFactory functions will sort the elements
 */
public enum SortingOrderChoices {
    /**
     * expected output: {a1,a2,..., an} where a1 &le; a2 &le; ... &le; an
     */
    ASCENDING,
    /**
     * expected output: {a1,a2,..., an} where a1 &ge; a2 &ge; ... &ge; an
     */
    DESCENDING
}
