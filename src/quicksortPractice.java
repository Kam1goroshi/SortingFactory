import java.util.Arrays;
import java.util.Random;

public class quicksortPractice {
    public static void quickSort(int[] arr, int first, int last) {
        if (last <= first || arr == null)
            return;
        Random rand = new Random();
        int pivotPosition = rand.nextInt(first, last+1);
        swap(arr, pivotPosition, last);
        pivotPosition = last;

        int l = first, r = last - 1;
        while (l < r) {
            while (l < last && arr[l] <= arr[pivotPosition]) {
                l++;
            }
            while (r >= 0 && arr[r] > arr[pivotPosition]) {
                r--;
            }
            if (l < r) {
                swap(arr, l, r);
            }
        }
        if (arr[l] > arr[last])
            swap(arr, l, last);

        quickSort(arr, first, l - 1);
        quickSort(arr, l + 1, last);
    }

    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int n = 100;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new Random().nextInt(n);
        }
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
