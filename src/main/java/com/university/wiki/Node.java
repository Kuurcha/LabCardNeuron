package com.university.wiki;

import java.util.Random;

public class Node {
    public final int x, y;
    public final double[] weights;

    private String assignedClass;

    public String getAssignedClass() {
        return assignedClass;
    }

    public void setAssignedClass(String assignedClass) {
        this.assignedClass = assignedClass;
    }

    public Node(int x, int  y, int inputSize, Random random) {
        this.x = x;
        this.y = y;
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
