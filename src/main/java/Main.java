import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final int LIST_LENGTH = 1_000_000;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Random randomNumber = new Random();
        List<Integer> randomListOfNumbers = new ArrayList<>();
        for (int i = 0; i < LIST_LENGTH; i++) {
            randomListOfNumbers.add(randomNumber.nextInt());
        }
        CallableThreadCalculator calculator = new CallableThreadCalculator(randomListOfNumbers);
        System.out.println("ExecutorService result: " + calculator.sum());

        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println("RecursiveTask result: " + commonPool
                .invoke(new CustomRecursiveTask(randomListOfNumbers)));
        commonPool.shutdownNow();
    }
}
