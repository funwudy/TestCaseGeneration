package model;

/**
 * Created by Fun on 2016/12/16.
 */
public class TriangleInput {
    public double a, b, c;
    public double va, vb, vc;
    public double fitness;

    public TriangleInput() {
    }

    public TriangleInput(TriangleInput input) {
        a = input.a;
        b = input.b;
        c = input.c;
        va = input.va;
        vb = input.vb;
        vc = input.vc;
        fitness = input.fitness;
    }

    public void modifyTo(TriangleInput input) {
        a = input.a;
        b = input.b;
        c = input.c;
        va = input.va;
        vb = input.vb;
        vc = input.vc;
        fitness = input.fitness;
    }
}
