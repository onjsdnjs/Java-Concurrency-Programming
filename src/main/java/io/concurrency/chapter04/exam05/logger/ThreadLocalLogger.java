package io.concurrency.chapter04.exam05.logger;

import java.util.ArrayList;
import java.util.List;

public class ThreadLocalLogger {

    private static final ThreadLocal<List<String>> THREAD_LOG = ThreadLocal.withInitial(ArrayList::new);

    public static void addLog(String log) {
        THREAD_LOG.get().add(log);
    }

    public static void printLog() {
        List<String> logs = THREAD_LOG.get();
        System.out.println("[" + Thread.currentThread().getName() + "] 로그: " + String.join(" -> ", logs));
    }

    public static void clearLog() {
        THREAD_LOG.remove();
    }

    static class ServiceA {
        public void process() {
            addLog("ServiceA 로직 수행");
        }
    }

    static class ServiceB {
        public void process() {
            addLog("ServiceB 로직 수행");
        }
    }

    static class ServiceC {
        public void process() {
            addLog("ServiceC 로직 수행");
        }
    }
}
