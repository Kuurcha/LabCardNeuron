package com.university.wiki.File;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.university.wiki.Model.Wine;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DatasetReader {

    public static List<Wine> readWine(URL winePath) {
        List<Wine> wines = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(winePath.openStream()))) {
            List<String[]> rows = reader.readAll();

            // Assuming the first row contains headers
            String[] header = rows.get(0);
//            System.out.println("Headers:");
//            for (String column : header) {
//                System.out.print(column + "\t");
//            }
//            System.out.println("\n");

            // Parse data rows into Wine objects
//            System.out.println("Data:");
            for (int i = 1; i < rows.size(); i++) { // Start from 1 to skip headers
                String[] row = rows.get(i);
                try {
                    Wine wine = parseWine(row); // Convert row to Wine object
                    wines.add(wine); // Add to the list
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing row: " + String.join(",", row));
                    e.printStackTrace();
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return wines;
    }

    // Helper method to convert a CSV row into a Wine object
    private static Wine parseWine(String[] row) {
        return new Wine(
                Double.parseDouble(row[0]),  // fixed acidity
                Double.parseDouble(row[1]),  // volatile acidity
                Double.parseDouble(row[2]),  // citric acid
                Double.parseDouble(row[3]),  // residual sugar
                Double.parseDouble(row[4]),  // chlorides
                Double.parseDouble(row[5]),  // free sulfur dioxide
                Double.parseDouble(row[6]),  // total sulfur dioxide
                Double.parseDouble(row[7]),  // density
                Double.parseDouble(row[8]),  // pH
                Double.parseDouble(row[9]),  // sulphates
                Double.parseDouble(row[10]), // alcohol
                Integer.parseInt(row[11])    // quality
        );
    }
}
