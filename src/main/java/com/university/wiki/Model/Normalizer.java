package com.university.wiki.Model;
import com.university.wiki.Node;
import  java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class    Normalizer {
    public static List<Wine> normalize(List<Wine> wines) {
        // Найти максимумы и минимумы для каждого признака
        double[] mins = new double[11];
        double[] maxs = new double[11];
        Arrays.fill(mins, Double.MAX_VALUE);
        Arrays.fill(maxs, Double.MIN_VALUE);

        for (Wine wine : wines) {
            double[] features = wine.toArray();
            for (int i = 0; i < features.length; i++) {
                mins[i] = Math.min(mins[i], features[i]);
                maxs[i] = Math.max(maxs[i], features[i]);
            }
        }

        // Нормализовать данные
        List<Wine> normalizedWines = new ArrayList<>();
        for (Wine wine : wines) {
            double[] features = wine.toArray();
            double[] normalizedFeatures = new double[features.length];
            for (int i = 0; i < features.length; i++) {
                normalizedFeatures[i] = (features[i] - mins[i]) / (maxs[i] - mins[i]);
            }
            normalizedWines.add(Wine.fromArray(normalizedFeatures, wine.getQuality()));
        }

        return normalizedWines;
    }
}