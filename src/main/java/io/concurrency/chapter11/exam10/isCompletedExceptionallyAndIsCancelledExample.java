package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class isCompletedExceptionallyAndIsCancelledExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return 10;
        });

        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return 10;
//            throw new RuntimeException("두 번째 작업에서 예외 발생");
        });

        CompletableFuture<Integer> cf3 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return 20;
        });

//        cf2.cancel(true);

        CompletableFuture<Integer> combinedFuture = cf1.thenCombine(cf2.exceptionally(e -> 15), (result1, result2) -> {

                if (cf2.isCancelled()) { // isDone() 과 구분할 수 있다
                    return 0;

                } else if (cf2.isCompletedExceptionally()) { // isDone() 과 구분할 수 있다
                    return result2;

                } else if(cf2.isDone()){
                    return result1 + result2;
                } else{

                    return -1;
                }
             })
            .thenCombine(cf3, (result2, result3) -> result2 + result3);

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
