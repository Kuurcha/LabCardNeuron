package com.university.wiki.Model;

import com.university.wiki.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class KohonenMap {
    private final Node[][] map;
    public final int width;
    public final int height;
    private final int inputSize;


    public Node[][] getAllNodes(){
        return map;
    }

    private void initNode(int inputSize){
            Random random = new Random();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    map[x][y] = new Node(x, y, inputSize, random);  // Initialize with x, y indices
                }
        }
    }

    public Node getNode(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return map[y][x];
        } else {
            throw new IllegalArgumentException("Неверные координаты: (" + x + ", " + y + ")");
        }
    }

    public KohonenMap(int width, int height, int inputSize) {
        this.width = width;
        this.height = height;
        this.inputSize = inputSize;
        this.map = new Node[width][height];
        initNode(inputSize);
    }

    // Найти ближайший нейрон к входным данным
    public Node findWinner(double[] input) {
        Node winner = map[0][0];
        double minDistance = winner.distanceTo(input);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Node current = map[i][j];
                double distance = current.distanceTo(input);
                if (distance < minDistance) {
                    minDistance = distance;
                    winner = current;
                }
            }
        }
        return winner;
    }


    public void train(double[][] data, int iterations, double initialLearningRate, double initialRadius) {
        Random random = new Random();
        double learningRate = initialLearningRate;
        double radius = initialRadius;

        for (int t = 0; t < iterations; t++) {
            for (double[] input : data) {
                Node winner = findWinner(input);

                // Обновляем веса победившего нейрона и его соседей
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        Node current = map[i][j];
                        double distance = Math.sqrt(Math.pow(i - winner.x, 2) + Math.pow(j - winner.y, 2));

                        if (distance < radius) {
                            double influence = Math.exp(-distance / (2 * Math.pow(radius, 2)));
                            current.adjustWeights(input, learningRate, influence);
                        }
                    }
                }
            }

            learningRate *= 0.9;
            radius *= 0.9;
        }
    }
    private int findClosestDataPoint(Node node, List<Wine> wines) {
        double minDistance = Double.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < wines.size(); i++) {
            Wine wine = wines.get(i);
            double[] features = wine.toArray();
            double distance = node.distanceTo(features);
            if (distance < minDistance) {
                minDistance = distance;
                index = i;
            }
        }
        return index;
    }

    public void printMap(List<Node> map, int width, int height, List<Wine> wines) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Node node = map.get(y * width + x);
                int closestIndex = findClosestDataPoint(node, wines);
                System.out.print(closestIndex + "\t");
            }
            System.out.println();
        }
    }



    public List<Double> computeNeighborDistances() {
        List<Double> distances = new ArrayList<>();
        int width = this.width;
        int height = this.height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Node node = this.map[x][y];

                // Проверяем соседей
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = x + dx;
                        int ny = y + dy;

                        if ((dx != 0 || dy != 0) && nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            Node neighbor = this.map[nx][ny];
                            double distance = node.distanceTo(neighbor.weights);
                            distances.add(distance);
                        }
                    }
                }
            }
        }
        return distances;
    }

}