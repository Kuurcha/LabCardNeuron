package com.university.wiki;

import com.university.wiki.File.DatasetReader;
import com.university.wiki.File.FileHelper;
import com.university.wiki.Model.KohonenMap;
import com.university.wiki.Model.KohonenMapPostProcessing;
import com.university.wiki.Model.Normalizer;
import com.university.wiki.Model.Wine;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main {
    public static void printWines(List<Wine> wines) {
        for (Wine wine : wines) {
            System.out.println(wine.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        String filePath = "/winequality-red.csv";
        URL resoucesPath = FileHelper.getResourcePath(filePath);
        List<Wine> wines = DatasetReader.readWine(resoucesPath);



        List<Wine> normalizedWines = Normalizer.normalize(wines);
        System.out.println("Normalized Wines:");
        printWines(normalizedWines);

        // Создаём карту Кохонена размером 10x10 с 11 признаками
        KohonenMap map = new KohonenMap(10, 10, 11);
        double[][] normalizedData = new double[normalizedWines.size()][];
        for (int i = 0; i < normalizedWines.size(); i++) {
            Wine wine = normalizedWines.get(i);
            normalizedData[i] =  wine.toArray();
        }
        // Обучаем карту
        map.train(normalizedData, 500, 0.2, 2);
        List<Double> distances = map.computeNeighborDistances();


        double minDistance = distances.stream().min(Double::compare).orElse(Double.NaN);
        double maxDistance = distances.stream().max(Double::compare).orElse(Double.NaN);
        double avgDistance = distances.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
        Collections.sort(distances);
        double median;
        if (distances.size() % 2 == 0) {
            // Если четное количество элементов, медиана - это среднее двух центральных элементов
            median = (distances.get(distances.size() / 2 - 1) + distances.get(distances.size() / 2)) / 2.0;
        } else {
            // Если нечетное количество элементов, медиана - центральный элемент
            median = distances.get(distances.size() / 2);
        }

        // Выводим результаты
        System.out.println("Минимальное расстояние: " + minDistance);
        System.out.println("Максимальное расстояние: " + maxDistance);
        System.out.println("Среднее расстояние: " + avgDistance);
        System.out.println("Медиана: " + median);

        KohonenMapPostProcessing clustering = new KohonenMapPostProcessing(map, median);
        Map<Integer, List<Node>> clusters =  clustering.createClusters();
        clustering.printClusters(clusters);


        System.out.println("Карта обучена!");
    }
}