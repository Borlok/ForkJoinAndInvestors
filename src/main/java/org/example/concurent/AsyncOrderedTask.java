package org.example.concurent;

import org.example.model.Investor;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * @author Erofeevskiy Yuriy on 26.11.2023
 */


public class AsyncOrderedTask extends RecursiveAction {
    private final List<Investor> investors;
    private final int threshold;
    private final int parts;

    public AsyncOrderedTask(List<Investor> investors, int threshold, int parts) {
        this.investors = investors;
        this.threshold = threshold;
        this.parts = parts;
    }

    @Override
    protected void compute() {
        try {
            if (investors.size() < threshold)
                someBusinessLogic();
            else
                divide();
        } finally {
            MyPhaser.deregister();
        }
    }

    private void someBusinessLogic() {
        BusinessLogic.handle(investors);
    }

    private void divide() {
        int size = investors.size(), remains = size % parts, step = size / parts + remains;
        System.out.printf("All size = %d | Step = %d | Parts = %d\n", size, step, parts); // TODO Delete
        for (int i = 0; i < size; i += step) {
            MyPhaser.register();
            int max = Math.min(size, i + step);
            new AsyncOrderedTask(investors.subList(i, max), threshold, parts).fork();
        }
    }
}
