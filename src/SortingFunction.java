/**
 * Interface for a sorting function
 * @param <T> the type of items to be sorted
 */
@FunctionalInterface
public interface SortingFunction<T>{
    /**
     * Warning: mutates input
     * @param array to be sorted
     * @return the steps needed to complete
     */
    long sort(T[] array);
}

