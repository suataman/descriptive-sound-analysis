/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ileri_prog_java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Ileri_prog_java {

    public static void main(String[] args) {
        String audioFilePath = "C:\\Users\\ASUS\\Downloads\\b.wav"; // Ses dosyasının yolu
        String csvFilePath = "C:\\Users\\ASUS\\Downloads\\output_analysis.csv"; // Çıktı CSV dosyasının yolu
        Locale.setDefault(Locale.US);
        try {
            // Harf bilgisini dosya adından çıkar
            String letter = extractLetterFromFileName(audioFilePath);

            // Genlikleri çıkar
            long startTime = System.nanoTime();
            
            
            List<Double> amplitudes = AmplitudeExtractor.extractAmplitudes(audioFilePath);
            
            long endTime = System.nanoTime(); // Bitiş zamanı
            System.out.println("Genlik Fonksiyonu çalışma süresi: " + (endTime - startTime) + " ns");
            
            startTime = System.nanoTime();
            
            // Descriptive analizi gerçekleştir
            DescriptiveAnalysis.AnalysisResult result = DescriptiveAnalysis.performAnalysis(amplitudes);
            
            endTime = System.nanoTime(); // Bitiş zamanı
            System.out.println(" Analiz Fonksiyon çalışma süresi: " + (endTime - startTime) + " ns");

            // CSV dosyasına yaz
            writeAnalysisToCSV(csvFilePath, letter, result);
            DatabaseConnection.insertAnalysisResult(letter, result);

            System.out.println("Descriptive analiz tamamlandı ve CSV kaydedildi: " + csvFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Harf bilgisini dosya adından çıkaran metot
    private static String extractLetterFromFileName(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        return fileName.substring(0, 1).toUpperCase(); // İlk harfi al ve büyük harfe çevir
    }

private static void writeAnalysisToCSV(String csvFilePath, String letter, DescriptiveAnalysis.AnalysisResult result) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath));
    writer.write("Letter,Mean,Median,StandardDeviation,Min,Max\n");

    // Sayıları doğru biçimde yazma (nokta ile)
    writer.write(String.format("%s,%.6f,%.6f,%.6f,%.6f,%.6f\n",
            letter, 
            result.mean, 
            result.median,
            result.stdDev,  
            result.min,
            result.max
    ));

    writer.close();
}

}
