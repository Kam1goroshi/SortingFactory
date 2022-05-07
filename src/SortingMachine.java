
public class SortingMachine<T extends Comparable<T>> {

    //#*#*#*#*#------\\   Attributes Section   //------#*#*#*#*#
    private SortingOrderChoices orderChoice;
    private SortingAlgorithmChoices algorithmChoice;
    private SortingFunction<T> sortingFunction;
    private SortingFunctionFactory<T> sortingFunctionFactory;

    //#*#*#*#*#------\\   Constructors Section   //------#*#*#*#*#
    SortingMachine(SortingAlgorithmChoices algorithmChoice, SortingOrderChoices orderChoice) {
        this.algorithmChoice = algorithmChoice;
        this.orderChoice = orderChoice;
        this.sortingFunctionFactory = new SortingFunctionFactory<>();
        sortingFunction = sortingFunctionFactory.generate(algorithmChoice, orderChoice);
    }

    //#*#*#*#*#------\\   Functionality Section   //------#*#*#*#*#
    public long sort(T[] array){
        return sortingFunction.sort(array);
    }

    //#*#*#*#*#------\\   Getters Section   //------#*#*#*#*#
    public SortingOrderChoices getOrderChoice() {
        return orderChoice;
    }

    public SortingAlgorithmChoices getAlgorithmChoice() {
        return algorithmChoice;
    }
}