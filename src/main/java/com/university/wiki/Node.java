package com.university.wiki;

import java.util.Random;

public class Node {
    public final int x, y;
    public final double[] weights;
    public int clusterLabel;
    private String assignedClass;

    public String getAssignedClass() {
        return assignedClass;
    }

    public void setAssignedClass(String assignedClass) {
        this.assignedClass = assignedClass;
    }

    public Node(int x, int  y, int inputSize, Random random, int clusterLabel) {
        this.x = x;
        this.y = y;
        this.clusterLabel = clusterLabel;
        this.weights = new double[inputSize];
        for (int i = 0; i < inputSize; i++) {
            weights[i] = random.nextDouble();
        }
    }



    public double distanceToEuclidian(double[] input) {
        double dist = 0.0;
        for (int i = 0; i < weights.length; i++) {
            dist += Math.pow(weights[i] - input[i], 2);
        }
        return Math.sqrt(dist);
    }

    public double distanceTo(double[] input) {
        double dist = 0.0;
        for (int i = 0; i < weights.length; i++) {
            dist += Math.pow(weights[i] - input[i], 2);
        }
        return Math.sqrt(dist);
    }



    public void adjustWeights1(double[] input, double learningRate, double influence) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * influence * (input[i] - weights[i]);
        }
    }
}
