import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Assignment8 assignment8 = new Assignment8();

        List<Integer> numbersList = new ArrayList<>();
        numbersList = Collections.synchronizedList(numbersList);

        List<CompletableFuture<Void>> tasks = new ArrayList<>();
        ExecutorService pool = Executors.newCachedThreadPool();

        for (int i = 0; i < 1000; i++ ){
          CompletableFuture<Void> task =
                  CompletableFuture.supplyAsync(assignment8::getNumbers,pool).thenAcceptAsync(numbersList::addAll,pool);
          tasks.add(task);
        }

        while(tasks.stream().filter(CompletableFuture::isDone).count() <1000){
            Thread.sleep(100);
        }

        Map<Integer, Integer> numMap = numbersList.stream().collect(Collectors.toMap(Function.identity(),sameValue -> 1,
                Integer::sum));
        System.out.println(numMap);

    }

    }
