import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CallableThreadCalculator {
    private static final int THREADS = 4;
    private static final int ITERATION_STEP = 250_000;
    private final List<Integer> randomListOfNumbers;

    public CallableThreadCalculator(List<Integer> randomListOfNumbers) {
        this.randomListOfNumbers = randomListOfNumbers;
    }

    Long sum() throws InterruptedException, ExecutionException {
        List<CallableThread> listThreads = new ArrayList<>();
        for (int i = 0; i < randomListOfNumbers.size(); i += ITERATION_STEP) {
            listThreads.add(new CallableThread(randomListOfNumbers
                    .subList(i, i + ITERATION_STEP)));
        }
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        List<Future<Long>> futures = executor.invokeAll(listThreads);
        executor.shutdown();
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        Long executorServiceResult = 0L;
        for (Future<Long> future : futures) {
            executorServiceResult += future.get();
        }
        return executorServiceResult;
    }
}
