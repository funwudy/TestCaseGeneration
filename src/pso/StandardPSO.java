package pso;

import model.TriangleInput;

import java.util.List;
import java.util.Random;

/**
 * Created by Fun on 2016/12/16.
 */
public class StandardPSO implements PSO {

    private Random random = new Random();

    private TriangleInput gbest;

    //factors about update rule
    private double c1 = 2.0, c2 = 2.0;
    private double r1, r2;
    private double wInertia = 0.05;

    public StandardPSO(TriangleInput gbest) {
        this.gbest = new TriangleInput(gbest);
    }

    @Override
    public void calculateFitness(TriangleInput p, TriangleInput pbest,
                                 double[] f, List<Integer> validFuncs) {
        double total = 0;
        for (int i : validFuncs) {
            total += 1 / (f[i] + 1);
        }
        p.fitness = total / validFuncs.size();
        if (pbest.fitness < p.fitness) {
            pbest.modifyTo(p);
        }
        if (gbest.fitness < p.fitness) {
            gbest.modifyTo(p);
        }
    }

    @Override
    public void updateParticle(TriangleInput p, TriangleInput pbest) {
        r1 = random.nextDouble();
        r2 = random.nextDouble();
        p.va = wInertia * p.va + c1 * r1 * (pbest.a - p.a) + c2 * r2 * (gbest.a - p.a);
        r1 = random.nextDouble();
        r2 = random.nextDouble();
        p.vb = wInertia * p.vb + c1 * r1 * (pbest.b - p.b) + c2 * r2 * (gbest.b - p.b);
        r1 = random.nextDouble();
        r2 = random.nextDouble();
        p.vc = wInertia * p.vc + c1 * r1 * (pbest.c - p.c) + c2 * r2 * (gbest.c - p.c);
        p.a += p.va;
        p.b += p.vb;
        p.c += p.vc;
    }
}
