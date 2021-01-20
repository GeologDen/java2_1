package lesson5;

import java.util.Arrays;

public class MainClass {

    private static final int SIZE = 10000000;
    private static final int HALF_SIZE = SIZE / 2;

    public float[] calculate(float[] arr) {
        for (int i = 0; i < arr.length; i++)
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        return arr;
    }

    public void oneThread() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1.0f);
        long leadTime = System.currentTimeMillis();
        calculate(arr);
        System.out.println("Время выполнения oneThread: " + (System.currentTimeMillis() - leadTime));
    }

    public void twoThreads() {
        float[] arr = new float[SIZE];
        float[] arr1 = new float[HALF_SIZE];
        float[] arr2 = new float[HALF_SIZE];
        Arrays.fill(arr, 1.0f);

        long leadTime = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, HALF_SIZE);
        System.arraycopy(arr, HALF_SIZE, arr2, 0, HALF_SIZE);

        new Thread() {
            public void run() {
                float[] a1 = calculate(arr1);
                System.arraycopy(a1, 0, arr1, 0, a1.length);
            }
        }.start();

        new Thread() {
            public void run() {
                float[] a2 = calculate(arr2);
                System.arraycopy(a2, 0, arr2, 0, a2.length);
            }
        }.start();

        System.arraycopy(arr1, 0, arr, 0, HALF_SIZE);
        System.arraycopy(arr2, 0, arr, HALF_SIZE, HALF_SIZE);
        System.out.println("Время выполнения twoThreads: " + (System.currentTimeMillis() - leadTime));
    }

    public static void main(String[] args) {

        MainClass o = new MainClass();

        o.oneThread();
        o.twoThreads();
    }


}
