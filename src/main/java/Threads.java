import java.util.Arrays;

public class Threads {


    private static final int SIZE = 10000000; //размер длинного массива
    private static final int HALF_SIZE = SIZE / 2; //размер разделенных массивов

    public float[] calculate(float[] arr) {
        for (int i = 0; i < arr.length; i++)
            arr[i] = (float) (arr[i] * Math.sin(0.2f + arr[i] / 5) * Math.cos(0.2f + arr[i] / 5) * Math.cos(0.4f + arr[i] / 2));
        return arr;

    }
    public void runOneThread() {
        float[] arr = new float[SIZE]; //одномерный длинный массив
        Arrays.fill(arr, 1.0f);
        long a = System.currentTimeMillis();
        calculate(arr);
        System.out.println("Первый поток: " + (System.currentTimeMillis() - a));
    }
    public void runTwoThreads() {
        float[] arr = new float[SIZE];
        float[] arr1 = new float[HALF_SIZE];
        float[] arr2 = new float[HALF_SIZE];
        Arrays.fill(arr, 1.0f);

        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, HALF_SIZE);
        System.arraycopy(arr, HALF_SIZE, arr2, 0, HALF_SIZE);

        new Threads() {

            public void run() {
                float[] a1 = calculate(arr1);
                System.arraycopy(a1, 0, arr1, 0, a1.length);
            }
        }.start();

        new Threads() {

            public void run() {
                float[] a2 = calculate(arr2);
                System.arraycopy(a2, 0, arr2, 0, a2.length);
            }
        }.start();

        System.arraycopy(arr1, 0, arr, 0, HALF_SIZE);
        System.arraycopy(arr2, 0, arr, HALF_SIZE, HALF_SIZE);
        System.out.println("Второй поток: " + (System.currentTimeMillis() - a));
    }

    void start() {
    }

    public static void main(String s[]) {
        Threads o = new Threads();
        o.runOneThread();
        o.runTwoThreads();
    }
}
