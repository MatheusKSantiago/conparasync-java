package overview;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceMain {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        Runnable t1 = () -> System.out.println("[TASK 1] - EXECUÇÃO ÚNICA");
        Runnable t2 = () -> System.out.println("[TASK 2] - PRIMEIRA E ÚNICA EXECUÇÃO APÓS ALGUM TEMPO");
        Runnable t3 = () -> System.out.println("[TASK 3] -  NOVA EXECUÇÃO");
        Runnable t4 = () -> System.out.println("[TASK 4] - NOVA EXECUÇÃO");
        executor.submit(t1);
        executor.schedule(t2,1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(t3,1000, 500,TimeUnit.MILLISECONDS);
        executor.scheduleWithFixedDelay(t4,0,1,TimeUnit.SECONDS);
        Thread.sleep(6000);
        executor.shutdown();

    }
}
