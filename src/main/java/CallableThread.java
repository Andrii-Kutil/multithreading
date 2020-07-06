import java.util.List;
import java.util.concurrent.Callable;

public class CallableThread implements Callable<Long> {
    private final List<Integer> randomListOfNumbers;

    public CallableThread(List<Integer> randomListOfNumbers) {
        this.randomListOfNumbers = randomListOfNumbers;
    }

    public Long call() {
        return randomListOfNumbers.stream()
                .mapToLong(Integer::longValue)
                .sum();
    }
}
