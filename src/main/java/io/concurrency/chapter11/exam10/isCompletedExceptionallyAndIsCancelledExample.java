package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class isCompletedExceptionallyAndIsCancelledExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<Integer> firstFuture = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return 10;
        });

        CompletableFuture<Integer> secondFuture = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
//            return 10;
            throw new RuntimeException("두 번째 작업에서 예외 발생");
        });

        CompletableFuture<Integer> thirdFuture = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return 20;
        });

        secondFuture.cancel(true);

        CompletableFuture<Integer> combinedFuture = firstFuture.thenCombine(secondFuture.exceptionally(e -> 15), (result1, result2) -> {

                if (secondFuture.isCancelled()) { // isDone() 과 구분할 수 있다
                    return 0;

                } else if (secondFuture.isCompletedExceptionally()) { // isDone() 과 구분할 수 있다
                    return result2;

                } else if(secondFuture.isDone()){
                    return result1 + result2;
                } else{

                    return -1;
                }
             })
            .thenCombine(thirdFuture, (result2, result3) -> result2 + result3);

        int result = combinedFuture.join(); // 결과 가져오기
        System.out.println("최종 결과: " + result);
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
