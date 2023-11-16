package io.concurrency.chapter11.exam12;

import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 2;
    private final int[] array;
    private final int start;
    private final int end;

    public CustomRecursiveTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start < THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }

            return sum;
        } else {
            int mid = start + (end - start) / 2;
            CustomRecursiveTask left = new CustomRecursiveTask(array, start, mid);
            CustomRecursiveTask right = new CustomRecursiveTask(array, mid, end);

            left.fork();
            long rightResult = right.compute();
            long leftResult = left.join();

            return leftResult + rightResult;
        }
    }
}
