package org.example.concurent;

import org.example.model.Investment;
import org.example.model.Investor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Erofeevskiy Yuriy on 05.12.2023
 */


public class BusinessLogic {

    public static void handle(List<Investor> investors) {
        try {
            System.out.println("Инвесторов: " + investors.size() + " с порядком инвестиций: " + investors.stream().map(x -> x.getInvestments().size()).collect(Collectors.toList())); // TODO Delete
            for (Investor investor : investors)
                for (int j = 0; j < investor.getInvestments().size(); j++)
                    Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static List<Investor> getUnhandledInvestors(int investorsNum, int investmentNum) {
        List<Investor> investors = new ArrayList<>();
        for (int i = 0; i < investorsNum; i++) {
            List<Investment> investments = new ArrayList<>();
            Investor investor = new Investor("Some Investor", investments);
            int investmentsNumber = (int) ((Math.random()) * investmentNum);
            for (int j = 0; j < investmentsNumber; j++)
                investments.add(new Investment("Good company", "somebody", 1000L));
            investors.add(investor);
        }
        return investors;
    }
}
