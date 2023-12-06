package org.example;

import org.example.concurent.BusinessLogic;
import org.example.concurent.MyPhaser;
import org.example.concurent.TaskManager;
import org.example.model.ExecutionMethod;
import org.example.model.Investor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Investor> investors = BusinessLogic.getUnhandledInvestors(1024, 120);
        MyPhaser.register();
        ExecutionMethod syncHandle = new ExecutionMethod("Последовательное выполнение со случайным порядком инвестиций", "Пример: [4,1,10,6,2,9,3,8,7,5]");
        ExecutionMethod commonMethodRandom = new ExecutionMethod("Метод со случайным порядком инвестиций (Практически равномерное распределение)", "Пример: [4,1,10,6,2,9,3,8,7,5]");
        ExecutionMethod commonMethodRandomPool = new ExecutionMethod("Метод со случайным порядком инвестиций по типу пула (Практически равномерное распределение)", "Пример: [4,1,10,6,2,9,3,8,7,5]");
        ExecutionMethod twoPointerExecutionRandom = new ExecutionMethod("Метод со случайным порядком инвестиций и применением алгоритма Two Pointers", "Пример: [4,1,10,6,2,9,3,8,7,5]");
        ExecutionMethod twoPointerExecutionRandomPool = new ExecutionMethod("Метод со случайным порядком инвестиций и применением алгоритма Two Pointers по типу пула", "Пример: [4,1,10,6,2,9,3,8,7,5]");
        ExecutionMethod commonMethodOrdered = new ExecutionMethod("Метод с отсортированным порядком инвестиций (Худший вариант)", "Пример: [1,2,3,4,5,6,7,8,9,10]");
        ExecutionMethod commonMethodOrderedPool = new ExecutionMethod("Метод с отсортированным порядком инвестиций по типу пула (Худший вариант с пулом)", "Пример: [1,2,3,4,5,6,7,8,9,10]");
        ExecutionMethod twoPointerExecutionOrdered = new ExecutionMethod("Метод с отсортированным порядком инвестиций и применением алгоритма Two Pointers", "Пример: [1,2,3,4,5,6,7,8,9,10]");
        ExecutionMethod twoPointerExecutionOrderedPool = new ExecutionMethod("Метод с отсортированным порядком инвестиций и применением алгоритма Two Pointers по типу пула", "Пример: [1,2,3,4,5,6,7,8,9,10]");

        syncHandle.represent();
        syncHandle.setTime(syncHandle(investors));

        commonMethodRandom.represent();
        commonMethodRandom.setTime(commonMethod(investors, 5));

        commonMethodRandomPool.represent();
        commonMethodRandomPool.setTime(commonMethod(investors, 10));

        twoPointerExecutionRandom.represent();
        twoPointerExecutionRandom.setTime(twoPointerExecution(investors, false));

        twoPointerExecutionRandomPool.represent();
        twoPointerExecutionRandomPool.setTime(twoPointerExecution(investors, true));

        investors.sort(Comparator.comparingInt(dividedStep -> dividedStep.getInvestments().size()));

        commonMethodOrdered.represent();
        commonMethodOrdered.setTime(commonMethod(investors, 5));

        commonMethodOrderedPool.represent();
        commonMethodOrderedPool.setTime(commonMethod(investors, 10));

        twoPointerExecutionOrdered.represent();
        twoPointerExecutionOrdered.setTime(twoPointerExecution(investors, false));

        twoPointerExecutionOrderedPool.represent();
        twoPointerExecutionOrderedPool.setTime(twoPointerExecution(investors, true));

        System.out.println("Результаты:"); // TODO Delete
        commonMethodRandom.showExecutionTime();
        twoPointerExecutionRandom.showExecutionTime();
        twoPointerExecutionRandomPool.showExecutionTime();
        commonMethodOrdered.showExecutionTime();
        commonMethodOrderedPool.showExecutionTime();
        twoPointerExecutionOrdered.showExecutionTime();
        twoPointerExecutionOrderedPool.showExecutionTime();

        System.out.println("\nНаш победитель:"); // TODO Delete
        ExecutionMethod method = Stream.of(commonMethodRandom,
                twoPointerExecutionRandom,
                twoPointerExecutionRandomPool,
                commonMethodOrdered,
                commonMethodOrderedPool,
                twoPointerExecutionOrdered,
                twoPointerExecutionOrderedPool).min(Comparator.comparingLong(ExecutionMethod::getTime))
                .orElse(new ExecutionMethod("Пустой", "Пустой"));
        method.showExecutionTime();
        MyPhaser.deregister();
    }

    private static long syncHandle(List<Investor> investors) {
        long start = System.currentTimeMillis(); // TODO Delete
        BusinessLogic.handle(investors);
        long end = System.currentTimeMillis(); // TODO Delete
        System.out.println("Result: " + (end - start) + " ms\n"); // TODO Delete
        return (end - start);
    }

    private static long commonMethod(List<Investor> investors, int parts) {
        TaskManager manager = new TaskManager(investors);
        long start = System.currentTimeMillis();
        manager.orderedExec(investors.size()  / 4, parts);
        MyPhaser.awaitAdvance();
        long end = System.currentTimeMillis();
        System.out.println("Result: " + (end - start) + " ms\n"); // TODO Delete
        return (end - start);
    }

    private static long twoPointerExecution(List<Investor> investors, boolean asPool) {
        TaskManager manager = new TaskManager(investors);
        long start = System.currentTimeMillis();
        manager.twoPointersExec(investors.size() / 4, asPool);
        MyPhaser.awaitAdvance();
        long end = System.currentTimeMillis();
        System.out.println("Result: " + (end - start) + " ms\n"); // TODO Delete
        return (end - start);
    }
}
