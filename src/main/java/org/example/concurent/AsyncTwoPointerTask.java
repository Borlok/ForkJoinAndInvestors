package org.example.concurent;

import org.example.model.Investor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Erofeevskiy Yuriy on 26.11.2023
 */


public class AsyncTwoPointerTask extends RecursiveAction {
    private final List<Investor> investors;
    private final int threshold;
    private final int parts;
    private final boolean asPool;

    public AsyncTwoPointerTask(List<Investor> investors, int threshold, int parts, boolean asPool) {
        this.investors = investors;
        this.threshold = threshold;
        this.parts = parts;
        this.asPool = asPool;
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
        int size = investors.size(), remains = size % parts, step = size / parts, dividedStep = step / 2, leftEnd = 0, leftStart, rightStart = size, rightEnd;
        System.out.printf("All size = %d | Step = %d | Parts = %d\n", size, asPool ? step / 2 : step, asPool ? parts * 2 : parts); // TODO Delete
        while (leftEnd < rightStart) {
            MyPhaser.register();
            leftStart = leftEnd;
            rightEnd = rightStart;
            leftEnd += Math.min(rightEnd - leftStart - dividedStep, dividedStep + remains);
            rightStart -= dividedStep;
            if (asPool) {
                MyPhaser.register();
                new AsyncTwoPointerTask(investors.subList(leftStart, leftEnd), threshold, parts, asPool).fork();
                new AsyncTwoPointerTask(investors.subList(rightStart, rightEnd), threshold, parts, asPool).fork();
            } else
                new AsyncTwoPointerTask(
                        Stream.of(investors.subList(leftStart, leftEnd), investors.subList(rightStart, rightEnd))
                                .flatMap(Collection::stream).collect(Collectors.toList()), threshold, parts, asPool).fork();
        }
    }
}
