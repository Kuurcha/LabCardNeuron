package com.university.wiki.Model;

import com.university.wiki.Node;

import java.util.*;


public class KohonenMap {
    private Node[][] map;
    public final int width;
    public final int height;
    private final int epochs;
    private final int inputSize;
    public int initialThreshold = -1;


    public void printClusterLabels() {
        System.out.println("Cluster Labels for Each Node:");

        // Подсчет количества узлов в каждом кластере
        Map<Integer, Integer> clusterCounts = new HashMap<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Node node = map[x][y];
                clusterCounts.put(node.clusterLabel, clusterCounts.getOrDefault(node.clusterLabel, 0) + 1);
                System.out.print("Node at (" + x + ", " + y + ") -> Cluster Label: " + node.clusterLabel + " | ");
            }
            System.out.println(); // Переход на новую строку после строки
        }

        // Отображение количества узлов в каждом кластере
        System.out.println("\nCluster Counts:");
        for (Map.Entry<Integer, Integer> entry : clusterCounts.entrySet()) {
            System.out.println("Cluster " + entry.getKey() + ": " + entry.getValue() + " nodes");
        }
    }

    public Node[][] getAllNodes(){
        return map;
    }

    private void initNodes(int inputSize){
            Random random = new Random();
            int nodeCount = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    nodeCount++;
                    map[x][y] = new Node(x, y, inputSize, random, nodeCount);  // Initialize with x, y indices
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

    public KohonenMap(int width, int height, int inputSize, int epochs) {
        this.width = width;
        this.height = height;
        this.inputSize = inputSize;
        this.epochs = epochs;
        this.map = new Node[width][height];
        initNodes(inputSize);
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
    public void adjustWeights(double[] input, double learningRate, double initialRadius, int currentIteration) {
        int winnerX = findWinner(input).getX();
        int winnerY = findWinner(input).getY();

        double sigma = initialRadius * Math.exp(-currentIteration / (double) totalIterations); // уменьшаем радиус влияния со временем
        double eta = learningRate * Math.exp(-currentIteration / (double) totalIterations); // уменьшаем скорость обучения со временем

        for (int i = 0; i < weights.length; i++) {
            double distance = euclideanDistance(new double[]{i, winnerX, winnerY}, new double[]{i, winnerX, winnerY});
            if (distance < sigma) {
                double influence = Math.exp(-distance * distance / (2 * sigma * sigma));
                weights[i] += eta * influence * (input[i] - weights[i]);
            }
        }
    }

    public void train(double[][] data, double initialLearningRate, double initialRadius) {
        Random random = new Random();
        double radius = initialRadius;
        double learningRate = initialLearningRate;
        this.computeNeighborDistances();
        for (int t = 0; t < epochs; t++) {
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



            learningRate = 0.9;
            radius = initialRadius * Math.exp(-t / (0.1 * epochs));
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

    public void assignClusterLabels(double initialThreshold) {
        boolean wasUpdated = true;
        double threshold = initialThreshold;

        while (wasUpdated) {
            wasUpdated = false;

            // Create a copy of the map to track changes without reaccessing it multiple times.
            Node[][] newMap = new Node[width][height];

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Node node = this.map[x][y];
                    Node bestNeighbor = null;
                    double bestDistance = Double.MAX_VALUE;

                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            int nx = x + dx;
                            int ny = y + dy;

                            if ((dx != 0 || dy != 0) && nx >= 0 && nx < width && ny >= 0 && ny < height) {
                                Node neighbor = this.map[nx][ny];
                                double distance = node.distanceTo(neighbor.weights);

                                if (distance <= threshold && distance < bestDistance) {
                                    bestDistance = distance;
                                    bestNeighbor = neighbor;
                                }
                            }
                        }
                    }

                    // Only update if a closer neighbor was found
                    if (bestNeighbor != null && node.clusterLabel != bestNeighbor.clusterLabel) {
                        node.clusterLabel = bestNeighbor.clusterLabel;
                        newMap[x][y] = node;
                        wasUpdated = true;
                    } else {
                        newMap[x][y] = node;
                    }
                }
            }

            // Copy the newMap back to the original map
            this.map = newMap;
            threshold *= 0.98; // Decrease threshold
        }
    }

//    public void assignClusterLabels(double threshold) {
//        boolean wasUpdated = true;
//        while (wasUpdated) {
//            wasUpdated = false;
//            for (int x = 0; x < width; x++) {
//                for (int y = 0; y < height; y++) {
//                    Node node = this.map[x][y];
//                    for (int dx = -1; dx <= 1; dx++) {
//                        for (int dy = -1; dy <= 1; dy++) {
//                            int nx = x + dx;
//                            int ny = y + dy;
//
//                            if ((dx != 0 || dy != 0) && nx >= 0 && nx < width && ny >= 0 && ny < height) {
//                                Node neighbor = this.map[nx][ny];
//                                double distance = node.distanceTo(neighbor.weights);
//
//                                if (distance <= threshold) {
//                                    node.clusterLabel = neighbor.clusterLabel;
//                                    map[x][y] = node;
//                                    wasUpdated = true;
//
//                                }
//                            }
//                        }
//                    }
//
//                }
//            }
//            threshold *= 0.9;
//        }
//    }

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