/**
 * Copyright <2022> <Georgios Pappas>
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
     * with the use of {@link #sortingFunctionFactory}
     *
     * @param algorithmChoice which sorting algorithm to use
     * @param orderChoice in which order to sort
     */
    SortingMachine(SortingAlgorithmChoices algorithmChoice, SortingOrderChoices orderChoice) {
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
     * @return the last chosen sorting order that has been set
     */
    public SortingOrderChoices getOrderChoice() {
        return orderChoice;
    }

    /**
     * @return the chosen algorithm that has last been set
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
        sortingFunction = sortingFunctionFactory.generate(algorithmChoice, orderChoice);
    }

    /**
     * Sets the algorithm choice for sorting machine and updates the sorting function
     * @param algorithmChoice choice for which algorithm to use
     */
    @SuppressWarnings("unused")
    public void setAlgorithmChoice(SortingAlgorithmChoices algorithmChoice) {
        this.algorithmChoice = algorithmChoice;
        sortingFunction = sortingFunctionFactory.generate(algorithmChoice, orderChoice);
    }
}