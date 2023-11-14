package io.concurrency.chapter11.exam09;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class WhenCompleteExample {
    public static void main(String[] args) throws InterruptedException {

    CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(500);
                    throw new RuntimeException("error");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return 10;
            }).whenComplete((r,e) ->{
                if(e != null){
                    System.out.println("Exception: " + e.getMessage());
                }else{
                    System.out.println("result: " + r);
                }
             });

        try {
            Thread.sleep(2000);
            cf.join();
        }catch(CompletionException e){
            System.out.println("예외 처리를 합니다");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
