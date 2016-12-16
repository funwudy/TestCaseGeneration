package pso;

import model.TriangleInput;

import java.util.List;

/**
 * Created by Fun on 2016/12/16.
 */
public interface PSO {

    void calculateFitness(TriangleInput p, TriangleInput pbest, double[] f, List<Integer> validFuncs);

    void updateParticle(TriangleInput p, TriangleInput pbest);
}
