import model.TriangleInput;
import pso.StandardPSO;
import util.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Fun on 2016/12/14.
 */

public class TriangleJudgeTest {

    private static final int POP_SIZE = 20;
    private static final int MAX_GENERATIONS = 1000;
    private static final int DEFAULT_FITNESS = 500;

    private Random random = new Random();

    private double[] f = new double[10];

    private Triangle judge3Edges(final int a, final int b, final int c) { // function to be tested
        for (int i = 0; i < f.length; i++) {
            f[i] = DEFAULT_FITNESS;
        }
        f[1] = a > 0 ? 0 : -a + 1;
        f[2] = b > 0 ? 0 : -b + 1;
        f[3] = c > 0 ? 0 : -c + 1;
        f[4] = a + b > c ? 0 : c - (a + b) + 1;
        f[5] = a + c > b ? 0 : b - (a + c) + 1;
        f[6] = b + c > a ? 0 : a - (b + c) + 1;
        if (a <= 0 || b <= 0 || c <= 0 || a + b <= c || a + c <= b || b + c <= a) { // branch 1
            return Triangle.NOT;
        }
        f[7] = Helper.minOf(Math.abs(a - b), Math.abs(a - c), Math.abs(b - c));
        if (a != b && a != c && b != c) { // branch 2
            return Triangle.GENERAL;
        }
        f[8] = Helper.maxOf(Math.abs(a - b), Math.abs(b - c));
        if (a == b && b == c) { // branch 3
            return Triangle.EQUILATERAL;
        } else {
            return Triangle.ISOSCELES;
        }
    }

    private void generateTestCase(int iterations) {
        int[] idxs = {1, 2, 3, 4, 5, 6, 7, 8};
        List<Integer> validFuncs = new ArrayList<>(); // corresponding branch func
        for (int i : idxs) {
            validFuncs.add(i);
        }

        TriangleInput[] colony = new TriangleInput[POP_SIZE];
        TriangleInput[] pbest = new TriangleInput[POP_SIZE];

        double totalGenerations = 0;
        outer:
        for (int iter = 0; iter < iterations; iter++) {

            for (int i = 0; i < colony.length; i++) {
                colony[i] = new TriangleInput();
                colony[i].a = random.nextInt(111) - 10;
                colony[i].b = random.nextInt(111) - 10;
                colony[i].c = random.nextInt(111) - 10;
                pbest[i] = new TriangleInput(colony[i]);
            }
            StandardPSO stdPSO = new StandardPSO(colony[0]);
            for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
                //calculate fitness
                for (int i = 0; i < colony.length; i++) {
                    judge3Edges((int) colony[i].a, (int) colony[i].b, (int) colony[i].c);
                    stdPSO.calculateFitness(colony[i], pbest[i], f, validFuncs);
                    if (colony[i].fitness - 1 < 0.0000001 && colony[i].fitness - 1 > -0.0000001) {
                        System.out.printf("Path covered at generation %d, inputs: %.2f, %.2f, %.2f\n", generation, colony[i].a, colony[i].b, colony[i].c);
                        totalGenerations += generation;
                        continue outer;
                    }
                }

                //update
                for (int i = 0; i < colony.length; i++) {
                    stdPSO.updateParticle(colony[i], pbest[i]);
                }
            }
        }
        double averageGenerations = totalGenerations / iterations;
        System.out.printf("Path covered after an average of %.2f generations, with %d iterations\n", averageGenerations, iterations);
    }

    private enum Triangle {
        NOT, GENERAL, ISOSCELES, EQUILATERAL, RIGHT_ANGLE;
    }

    public static void main(String[] args) {
        TriangleJudgeTest test = new TriangleJudgeTest();
        test.generateTestCase(5000);
    }
}
