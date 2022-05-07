package sorting_machine;

/**
 * Sorts inputs based on chosen settings
 */
public class SortingMachine<T extends Comparable<T>> {

    //#*#*#*#*#------\\   Attributes   //------#*#*#*#*#
    private SortingOrderChoices orderChoice; // in which order to sort
    private SortingAlgorithmChoices algorithmChoice; //which algorithm to use
    private SortingFunctionFactory.SortingFunction<T> sortingFunction; //Sorting function callback
    private final SortingFunctionFactory<T> sortingFunctionFactory; //Method generator

    //#*#*#*#*#------\\   Constructors   //------#*#*#*#*#

    /**
     * Initializes the attributes of the generated object and generates a sorting function through <br>
     * with the use of SortingFunctionFactory
     *
     * @see SortingFunctionFactory
     * @param algorithmChoice which sorting algorithm to use
     * @param orderChoice in which order to sort
     */
    public SortingMachine(SortingAlgorithmChoices algorithmChoice, SortingOrderChoices orderChoice) {
        this.algorithmChoice = algorithmChoice;
        this.orderChoice = orderChoice;
        this.sortingFunctionFactory = new SortingFunctionFactory<>();
        sortingFunction = sortingFunctionFactory.generate(algorithmChoice, orderChoice);
    }

    //#*#*#*#*#------\\   Functionality   //------#*#*#*#*#
    public long sort(T[] array){
        return sortingFunction.sort(array);
    }

    //#*#*#*#*#------\\   Getters and Setters   //------#*#*#*#*#

    /**
     * @return the last chosen sorting order setting
     */
    public SortingOrderChoices getOrderChoice() {
        return orderChoice;
    }

    /**
     * @return the chosen algorithm setting
     */
    public SortingAlgorithmChoices getAlgorithmChoice() {
        return algorithmChoice;
    }

    /**
     * Sets the sorting order choice for sorting machine and updates the sorting function
     * @param orderChoice desired sorting order for sorting result
     */
    @SuppressWarnings("unused")
    public void setOrderChoice(SortingOrderChoices orderChoice) {
        this.orderChoice = orderChoice;
        updateSortingFunction();
    }

    /**
     * Sets the algorithm choice for sorting machine and updates the sorting function
     *
     * @param algorithmChoice choice for which algorithm to use
     */
    @SuppressWarnings("unused")
    public void setAlgorithmChoice(SortingAlgorithmChoices algorithmChoice) {
        this.algorithmChoice = algorithmChoice;
        updateSortingFunction();
    }

    /**
     * Update this.sortingFunction based on algorithmChoice and orderChoice
     */
    private void updateSortingFunction(){
        sortingFunction = sortingFunctionFactory.generate(algorithmChoice, orderChoice);
    }
}