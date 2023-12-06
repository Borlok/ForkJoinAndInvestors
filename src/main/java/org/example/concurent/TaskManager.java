package org.example.concurent;

import org.example.model.Investor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Erofeevskiy Yuriy on 26.11.2023
 */


public class TaskManager {
    private final List<Investor> tasks;
    private final int CORES = 5;
    private final ForkJoinPool pool = new ForkJoinPool(CORES);

    public TaskManager(List<Investor> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }
    public void orderedExec(int thresholdSize, int parts) {
        MyPhaser.register();
        pool.submit(new AsyncOrderedTask(tasks, Math.max(100, thresholdSize), parts));
    }

    public void twoPointersExec(int thresholdSize, boolean asPool) {
        MyPhaser.register();
        pool.submit(new AsyncTwoPointerTask(tasks, Math.max(100, thresholdSize), CORES, asPool));
    }
}
