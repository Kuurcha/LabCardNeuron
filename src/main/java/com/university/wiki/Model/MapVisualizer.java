//package com.university.wiki.Model;
//
//import com.university.wiki.Node;
//import org.knowm.xchart.HeatMapChart;
//import org.knowm.xchart.HeatMapChartBuilder;
//import org.knowm.xchart.SwingWrapper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MapVisualizer {
//
//
//    public static void visualizeSOMGrid(int width, int height, double[][] somWeights) {
//
//    }
//    public void visualizeMap(KohonenMap map, List<Wine> wines) {
//        int height = map.height;
//        int width = map.width;
//
//        List<Double> xData = new ArrayList<>();
//        List<Double> yData = new ArrayList<>();
//        List<Integer> colorData = new ArrayList<>(); // Color based on class or activation strength
//
//        // Create a 2D grid for the SOM and assign colors (optional)
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                xData.add((double) x);  // X-coordinate of the neuron
//                yData.add((double) y);  // Y-coordinate of the neuron
//                // Example: Color by activation strength (you can modify this)
//                colorData.add((int) (map[x][y] * 255));  // Activation strength as color intensity
//            }
//        }
//
//        // Create the chart
//        XYChart chart = new XYChartBuilder().width(800).height(600).title("SOM Grid Visualization").build();
//        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
//
//        // Scatter plot (neurons as points)
//        chart.addSeries("SOM Neurons", xData, yData).setMarker(SeriesMarker.CIRCLE).setMarkerColor(java.awt.Color.RED);
//
//        // Display the chart
//        new SwingWrapper(chart).displayChart();
//
////        int width = map.width;
////        int height = map.height;
////
////
////        List<Number[]> heatData = new ArrayList<>();
////
////        // Заполняем список данными на основе среднего качества ближайших вин
////        for (int y = 0; y < height; y++) {
////            Number[] row = new Number[width];
////            for (int x = 0; x < width; x++) {
////                Node node = map.getNode(x, y);
////                double averageQuality = calculateAverageQuality(node, wines);
////                row[x] = averageQuality;
////            }
////            heatData.add(row);
////        }
////
////        // Создаем график тепловой карты
////        HeatMapChart chart = new HeatMapChartBuilder()
////                .width(800).height(600)
////                .title("Kohonen Map")
////                .xAxisTitle("X").yAxisTitle("Y")
////                .build();
////
////        // Добавляем данные в график
////        List<String> xLabels = generateLabels(width);
////        List<String> yLabels = generateLabels(height);
////
////        System.out.println("HeatData rows: " + heatData.size());
////        System.out.println("HeatData columns: " + (heatData.isEmpty() ? 0 : heatData.get(0).length));
////        System.out.println("xLabels: " + xLabels.size());
////        System.out.println("yLabels: " + yLabels.size());
////        chart.addSeries("Quality Heatmap", xLabels, yLabels, heatData);
////
////        // Показываем график
////        new SwingWrapper<>(chart).displayChart();
//    }
//
//    private double calculateAverageQuality(Node node, List<Wine> wines) {
//        double sumQuality = 0;
//        int count = 0;
//
//        for (Wine wine : wines) {
//            double[] features = wine.toArray();
//            double distance = node.distanceTo(features);
//            if (distance < 1.0) { // Задаем порог близости
//                sumQuality += wine.getQuality();
//                count++;
//            }
//        }
//
//        return count > 0 ? sumQuality / count : 0; // Среднее качество или 0
//    }
//
//    private List<String> generateLabels(int count) {
//        List<String> labels = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            labels.add(String.valueOf(i));
//        }
//        return labels;
//    }
//}
