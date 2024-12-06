package com.university.wiki.Model;

import com.university.wiki.Node;

import java.util.*;

public class KohonenMapPostProcessing {
    private final KohonenMap kohonenMap;
    private final double distanceThreshold; // Пороговое расстояние для объединения

    public KohonenMap getMap(){
        return kohonenMap;
    }

    public KohonenMapPostProcessing(KohonenMap kohonenMap, double distanceThreshold) {
        this.kohonenMap = kohonenMap;
        this.distanceThreshold = distanceThreshold;
    }


    // Построение кластеров на основании расстояний
    public Map<Integer, List<Node>> createClusters() {
        int width = kohonenMap.width;
        int height = kohonenMap.height;
        Node[][] map = kohonenMap.getAllNodes();

        boolean[][] visited = new boolean[width][height];
        Map<Integer, List<Node>> clusters = new HashMap<>();
        int clusterId = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!visited[x][y]) {
                    List<Node> cluster = new ArrayList<>();
                    expandCluster(map, x, y, visited, cluster);
                    clusters.put(clusterId++, cluster);
                }
            }
        }
        return clusters;
    }

    // Расширение кластера на основе соседей
    private void expandCluster(Node[][] map, int x, int y, boolean[][] visited, List<Node> cluster) {
        int width = kohonenMap.width;
        int height = kohonenMap.height;


        Queue<Node> queue = new LinkedList<>();
        queue.add(map[x][y]);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (!visited[current.x][current.y]) {
                visited[current.x][current.y] = true;
                cluster.add(current);

                // Добавляем соседей, если они подходят
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = current.x + dx;
                        int ny = current.y + dy;

                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            Node neighbor = map[nx][ny];
                            if (!visited[nx][ny] && current.distanceTo(neighbor.weights)< distanceThreshold) {
                                queue.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
    }

    // Вывод кластеров
    public void printClusters(Map<Integer, List<Node>> clusters) {
        for (Map.Entry<Integer, List<Node>> entry : clusters.entrySet()) {
            System.out.println("Cluster ID: " + entry.getKey());
            for (Node node : entry.getValue()) {
                System.out.printf("  Node at (%d, %d) with weights: %s%n",
                        node.x, node.y, Arrays.toString(node.weights));
            }
        }
    }
}
