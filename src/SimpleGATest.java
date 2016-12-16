import java.util.Random;

/**
 * Created by Fun on 2016/11/26.
 */
public class SimpleGATest {

    private static final int SAMPLE_SIZE = 10;
    private static final int BITS = 3;
    private static final int PARAMETER_NUMBER = 2;
    private static final int MAX_GENERATIONS = 1000;
    private static final int MUTATION_PROBABILITY = 10;
    private static final int TERMINATION_THRESHOLD = 980;

    public static void main(String[] args) {
        Random random = new Random();

        long[] colony = new long[SAMPLE_SIZE];
        long maxGene = (long) Math.pow(2, BITS * PARAMETER_NUMBER) - 1;
        long[] fitnesses = new long[SAMPLE_SIZE];

        //initialize
        for (int i = 0; i < colony.length; i++) {
            colony[i] = random.nextInt((int) (maxGene + 1));
        }
        long[] originals = colony;

        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
            //count fitness
            long totalFitness = 0;
            for (int i = 0; i < colony.length; i++) {
                long x1 = (colony[i] >>> BITS) & (long) (Math.pow(2, BITS) - 1);
                long x2 = colony[i] & (long) (Math.pow(2, BITS) - 1);
                fitnesses[i] = x1 * x1 + x2 * x2;
                totalFitness += fitnesses[i];
            }
            if (totalFitness >= 980) {
                System.out.println("Maximum at generation " + generation);
                break;
            }

            //select
            long[] selection = new long[SAMPLE_SIZE];
            for (int i = 0; i < selection.length; i++) {
                long fitnessSoFar = 0;
                long slice = random.nextInt((int) totalFitness);
                for (int j = 0; j < fitnesses.length; j++) {
                    fitnessSoFar += fitnesses[j];
                    if (fitnessSoFar > slice) {
                        selection[i] = colony[j];
                        break;
                    }
                }
            }

            //intersect
            for (int i = 0; i < selection.length; i += 2) {
                int intersectBit = random.nextInt(BITS * PARAMETER_NUMBER - 1) + 1;
                long exchangeBits1 = selection[i] & (long) (Math.pow(2, intersectBit) - 1);
                long exchangeBits2 = selection[i + 1] & (long) (Math.pow(2, intersectBit) - 1);
                selection[i] = (selection[i] >>> intersectBit << intersectBit) | exchangeBits2;
                selection[i + 1] =
                        (selection[i + 1] >>> intersectBit << intersectBit) | exchangeBits1;
            }

            //mutate
            for (int i = 0; i < selection.length; i++) {
                if (random.nextInt(100) < MUTATION_PROBABILITY) {
                    int mutationBit = random.nextInt(BITS * PARAMETER_NUMBER);
                    selection[i] ^= (1 << mutationBit);
                }
            }

            //one generation complete
            for (int i = 0; i < colony.length; i++) {
                System.out.print(Long.toBinaryString(colony[i]) + " ");
            }
            System.out.println("   total: " + totalFitness);
            colony = selection;
        }

        for (int i = 0; i < colony.length; i++) {
            System.out.println(Long.toBinaryString(originals[i]) + " -> "
                    + Long.toBinaryString(colony[i]));
        }
    }
}
