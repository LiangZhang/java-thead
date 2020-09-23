package com.zlsoft;

public class TestCpu {
    public static void main(String[] args) {

        new Thread(() -> {
            while(true) {
                try {

                    Thread.yield();
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
