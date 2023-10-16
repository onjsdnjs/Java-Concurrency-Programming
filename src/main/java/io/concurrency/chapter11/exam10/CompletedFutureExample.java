package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletedFutureExample {
    public static void main(String[] args) {
        CompletableFuture<User> userFuture = getUserInfoAsync(123);

        // 사용자 정보를 가져온 후 처리
        userFuture.thenAccept(user -> {
            System.out.println("사용자 정보: " + user);
            // 추가로 사용자 정보를 처리 하는 작업 수행 가능
        });

    }

    // 사용자 정보를 가져 오는 비동기 메서드 (실제 로는 DB 또는 원격 서비스 와 상호 작용)
    static CompletableFuture<User> getUserInfoAsync(int userId) {

        // 이미 계산된 사용자 정보를 completedFuture 로 반환
        // 비동기 작업이 필요 하지 않은 경우
        // 비동기 작업의 결과를 동기적 으로 처리할 때
        return CompletableFuture.completedFuture(new User(userId, "John Doe", "john@example.com"));
    }

    static class User {
        private int id;
        private String name;
        private String email;

        public User(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        // Getter 및 toString() 메서드 생략
    }
}
