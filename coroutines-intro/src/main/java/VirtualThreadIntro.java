import java.time.Duration;
import java.time.LocalDateTime;

import static java.util.stream.IntStream.range;

/**
 * Created by jofisaes on 22/06/2022
 */
public class VirtualThreadIntro {
    public static void main(String[] args) {
        var start = LocalDateTime.now();
        range(0, 1000).mapToObj(i -> Thread.startVirtualThread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Done!" + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        })).toList().forEach(task -> {
            try {
                task.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        System.out.println(Duration.between(start, LocalDateTime.now()).toMillis());

    }
}
