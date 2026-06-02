package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static final int NUMBEROPS_M = 10000;
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> sizesOfDS = new AList<>();
        AList<Double> doubleTimes = new AList<>();
        AList<Integer> numberOps = new AList<>();
        double timeInSeconds;
        Stopwatch sw;
        for (int i = 0; i < 8; i++) {
            sizesOfDS.addLast( 1000* (int) Math.pow(2, i));
        }


        /*
          1.) for each size
          2.) create a new experiment list that you will add the number of items with size
          3.) do 2 by for loop
         */
        for(int k = 0; k < sizesOfDS.size(); k++) {
            numberOps.addLast(NUMBEROPS_M);
            SLList<Integer> experimentList = new SLList<>(); //create an SList
            for(int j = 0; j < sizesOfDS.get(k); j++) { //Add N items to the SLList
                experimentList.addFirst(j);
            }
            sw = new Stopwatch(); //start timer
            int intDummy;
            for (int i = 0; i < NUMBEROPS_M; i++) { //perform M getLast operations on the SList
                intDummy = experimentList.getLast();
            }
            timeInSeconds = sw.elapsedTime();
            doubleTimes.addLast(timeInSeconds);
        }
        printTimingTable(sizesOfDS, doubleTimes, numberOps);
    }

}
