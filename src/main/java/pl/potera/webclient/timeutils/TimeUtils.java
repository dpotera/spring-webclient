package pl.potera.webclient.timeutils;

public class TimeUtils {

    public static void measureTime(Runnable fun, String log) {
        long startTime = System.nanoTime();
        fun.run();
        long timeElapsed = System.nanoTime() - startTime;
        System.out.println(log + " : " + (timeElapsed / 1000000) + "ms");
    }
}
