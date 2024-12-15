package com.university.wiki.Model;

public class StatHelper {
    public static void checkFeatureStats(double[][] data) {
        int ncols = data[0].length;

        for (int j = 0; j < ncols; j++) {
            double sum = 0;
            double sumSquared = 0;

            for (int i = 0; i < data.length; i++) {
                sum += data[i][j];
                sumSquared += Math.pow(data[i][j], 2);
            }

            double mean = sum / data.length;
            double variance = (sumSquared / data.length) - Math.pow(mean, 2);
            double stdDev = Math.sqrt(variance);

            System.out.println("Признак " + j + ": среднее = " + mean + ", стандартное отклонение = " + stdDev);
        }
    }
    public static void checkFeatureRanges(double[][] data) {
        int ncols = data[0].length;

        for (int j = 0; j < ncols; j++) {
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            for (int i = 0; i < data.length; i++) {
                min = Math.min(min, data[i][j]);
                max = Math.max(max, data[i][j]);
            }

            System.out.println("Признак " + j + ": мин = " + min + ", макс = " + max);
        }
    }
}
