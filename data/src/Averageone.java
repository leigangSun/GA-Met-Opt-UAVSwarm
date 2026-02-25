import GA.Algorithm;
import MDVRP.Manager;
import MDVRP.Solution;

public class Averageone {
    /*
    Runs through all problems multiple times and saves average result
     */
    public static void main(String[] args) {

       String problem = "p31";
       Manager manager = new Manager("data/problems/" + problem, 0.5);
       Algorithm ga = new Algorithm(manager);
       Solution solution = ga.run();                                                    // Run algorithm
       double solutionCost = solution.getIndividual().getFitness();
       System.out.println("最优距离为 " + solutionCost);
    }
}
