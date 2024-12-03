package com.university.wiki;

import com.university.wiki.File.DatasetReader;
import com.university.wiki.File.FileHelper;
import com.university.wiki.Model.Wine;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "/winequality-red.csv";
        URL resoucesPath = FileHelper.getResourcePath(filePath);
        List<Wine> wines = DatasetReader.readWine(resoucesPath);
        for (Wine w: wines){
            System.out.println(w.toString());
        }
        System.out.println("Hello world!");
    }
}