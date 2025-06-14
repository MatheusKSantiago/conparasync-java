package overview;

import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceMain {

    public static int THREEAD_POOL_SIZE = 3;
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREEAD_POOL_SIZE);

        List<Callable<String>> tasks = List.of(
            ()->{Thread.sleep(250);return "TASK 1";},
            ()->{Thread.sleep(250);return "TASK 2";},
            ()->{Thread.sleep(250);return "TASK 3";},
            ()->{Thread.sleep(250);return "TASK 4";},
            ()->{Thread.sleep(250);return "TASK 5";}
        );

        List<Future<String>> futures = tasks.stream().map(executor::submit).toList();

        futures.forEach(f -> CompletableFuture.runAsync(()->{
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }));

        executor.shutdown();
    }
}
