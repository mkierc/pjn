package lab3;

import java.util.TreeMap;


public class BayesianClassification {

    public static void main(String[] args) {
        TreeMap<String, Double> bayesianMap = new TreeMap<>();
        TreeMap<Float, Double> errorStatisticMap =  ReadData.countErrorStatistic();
        TreeMap<String, Double> formsStatisticMap = ReadData.countFormsStatistic("publ");

        // TODO: Użyć obliczonych statystyk do znalezienia poprawki przez klasyfikator Bayesa

    }
}
