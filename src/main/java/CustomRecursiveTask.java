import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 250_000;
    private final List<Integer> randomListOfNumbers;

    public CustomRecursiveTask(List<Integer> randomListOfNumbers) {
        this.randomListOfNumbers = randomListOfNumbers;
    }

    @Override
    protected Long compute() {
        if (randomListOfNumbers.size() > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubTasks())
                    .stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        } else {
            return sum(randomListOfNumbers);
        }
    }
 
    private Collection<CustomRecursiveTask> createSubTasks() {
        List<CustomRecursiveTask> dividedTasks = new ArrayList<>();
        dividedTasks.add(new CustomRecursiveTask(randomListOfNumbers
                        .subList(0, randomListOfNumbers.size() / 2)));
        dividedTasks.add(new CustomRecursiveTask(randomListOfNumbers
                .subList(randomListOfNumbers.size() / 2, randomListOfNumbers.size())));
        return dividedTasks;
    }
 
    private Long sum(List<Integer> randomListOfNumbers) {
        return randomListOfNumbers.stream()
                .mapToLong(Integer::longValue)
                .sum();
    }
}
