package io.concurrency.chapter01.exam01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessExample {
    public static void main(String[] args) {
        try {
            ProcessBuilder processBuilder1 = new ProcessBuilder("ls", "-l");
            Process process1 = processBuilder1.start();

            ProcessBuilder processBuilder2 = new ProcessBuilder("ls", "none");
            Process process2 = processBuilder2.start();

            // 프로세스의 표준 출력 스트림을 읽어옵니다.
            BufferedReader reader = new BufferedReader(new InputStreamReader(process1.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("표준 출력: " + line);
            }

            // 프로세스의 표준 오류 스트림을 읽어옵니다.
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process2.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println("표준 오류: " + errorLine);
            }

            // 프로세스가 완료될 때까지 대기합니다.
            int exitCode1 = process1.waitFor();
            int exitCode2 = process2.waitFor();
            System.out.println("프로세스 1 종료 코드: " + exitCode1);
            System.out.println("프로세스 2 종료 코드: " + exitCode2);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
