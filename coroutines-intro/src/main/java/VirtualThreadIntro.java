import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

/**
 * Created by jofisaes on 22/06/2022
 */
public class VirtualThreadIntro {
    public static void main(String[] args) throws InterruptedException {
        var start = LocalDateTime.now();
        range(0, 1000).mapToObj(i -> Thread.startVirtualThread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Done!" + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        })).collect(Collectors.toList()).forEach(task -> {
            try {
                task.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        System.out.println(Duration.between(start, LocalDateTime.now()).toMillis());

    }
}
