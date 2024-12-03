package com.university.wiki;

import java.util.Random;

public class Node {
    private final int x, y;
    private final double[] weights;

    public Node(int inputSize, Random random) {
        this.x = random.nextInt(); // Set x, y positions based on SOM initialization
        this.y = random.nextInt();
        this.weights = new double[inputSize];
        for (int i = 0; i < inputSize; i++) {
            weights[i] = random.nextDouble();
        }
    }

    public double distanceTo(double[] input) {
        double dist = 0.0;
        for (int i = 0; i < weights.length; i++) {
            dist += Math.pow(weights[i] - input[i], 2);
        }
        return Math.sqrt(dist);
    }

    public void adjustWeights(double[] input, double learningRate, double influence) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * influence * (input[i] - weights[i]);
        }
    }
}
