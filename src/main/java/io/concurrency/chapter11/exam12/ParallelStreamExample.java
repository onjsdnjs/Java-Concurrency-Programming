package io.concurrency.chapter11.exam12;

import java.util.stream.IntStream;

public class ParallelStreamExample{
    public static void main(String[] args) {

        int[] array = IntStream.range(0, 10).toArray();
        long sum = IntStream.of(array).parallel().sum();

        System.out.println("The sum is " + sum);
    }
}
