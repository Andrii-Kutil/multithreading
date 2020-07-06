import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 250_000;
    private final List<Integer> numbers;

    public CustomRecursiveTask(List<Integer> numbers) {
        this.numbers = numbers;
    }

    @Override
    protected Long compute() {
        if (numbers.size() > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubTasks())
                    .stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        } else {
            return sum(numbers);
        }
    }
 
    private Collection<CustomRecursiveTask> createSubTasks() {
        List<CustomRecursiveTask> dividedTasks = new ArrayList<>();
        dividedTasks.add(new CustomRecursiveTask(numbers
                        .subList(0, numbers.size() / 2)));
        dividedTasks.add(new CustomRecursiveTask(numbers
                .subList(numbers.size() / 2, numbers.size())));
        return dividedTasks;
    }
 
    private Long sum(List<Integer> numbers) {
        return numbers.stream()
                .mapToLong(Integer::longValue)
                .sum();
    }
}
