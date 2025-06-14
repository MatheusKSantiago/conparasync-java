package overview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class FixedRateDelayMain {
    public static AtomicReference<List<Long>> tempos = new AtomicReference<>(new ArrayList<>());
    public static AtomicReference<List<Long>> tempos1 = new AtomicReference<>(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


        Runnable task = () -> {
            tempos.updateAndGet(t ->{
                List<Long> novosTempos = new ArrayList<>(t);
                novosTempos.add(System.currentTimeMillis());
                return novosTempos;
            });
        };
        Runnable task1 = () -> {
            tempos1.updateAndGet(t ->{
                List<Long> novosTempos = new ArrayList<>(t);
                novosTempos.add(System.currentTimeMillis());
                return novosTempos;
            });
        };

        executor.scheduleWithFixedDelay(task,0,1, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(task1,0,1, TimeUnit.MILLISECONDS);

        Thread.sleep(100);
        executor.shutdown();
        Map.Entry<Double,Double> r1 = desvioPadraoDiferenca(tempos.get());
        Map.Entry<Double,Double> r2 = desvioPadraoDiferenca(tempos1.get());
        System.out.println("Número de execuções, Média Desvio padrão da diferença entre duas execuções (milisegundos)");
        System.out.printf("[Fixed Delay]   N = %d M = %.2f  D = %.3f\n",tempos.get().size(),r1.getKey(),r1.getValue());
        System.out.printf("[At fixed rate] N = %d M = %.2f  D = %.3f\n",tempos1.get().size(),r2.getKey(),r2.getValue());

    }

    public static Map.Entry<Double,Double> desvioPadraoDiferenca(List<Long> timestamps){
        if(timestamps.size()%2 != 0){
            timestamps.remove(timestamps.size() - 1);
        }
        int size = timestamps.size();
        int N = size/2;

        List<Double> diferencas = IntStream.range(0,N).mapToDouble(l ->  timestamps.get((l*2)+1).doubleValue() - timestamps.get(l*2).doubleValue()).boxed().toList();
        double media = diferencas.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        double desvioPadrao =  Math.sqrt(diferencas.stream().reduce(0.0,(a,b)-> a + Math.pow((media - b),2))/N);
        return Map.entry(media,desvioPadrao);

    }
}
