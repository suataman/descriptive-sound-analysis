/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ileri_prog_java;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DescriptiveAnalysis {

    // Descriptive analiz sonuçlarını içeren sınıf
    public static class AnalysisResult {
        public double mean;
        public double median;
        public double stdDev;
        public double min;
        public double max;

        public AnalysisResult(double mean, double median, double stdDev, double min, double max) {
            this.mean = mean;
            this.median = median;
            this.stdDev = stdDev;
            this.min = min;
            this.max = max;
        }
    }

    // Descriptive analizi başlatan metot
    public static AnalysisResult performAnalysis(List<Double> amplitudes) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Thread'lerle analiz işlemleri
        Future<Double> meanFuture = executor.submit(() -> calculateMean(amplitudes));
        Future<Double> medianFuture = executor.submit(() -> calculateMedian(amplitudes));
        Future<Double> stdDevFuture = executor.submit(() -> calculateStandardDeviation(amplitudes));
        Future<Double> minFuture = executor.submit(() -> Collections.min(amplitudes));
        Future<Double> maxFuture = executor.submit(() -> Collections.max(amplitudes));

        // Sonuçları al
        double mean = meanFuture.get();
        double median = medianFuture.get();
        double stdDev = stdDevFuture.get();
        double min = minFuture.get();
        double max = maxFuture.get();

        executor.shutdown(); // Thread havuzunu kapat

        return new AnalysisResult(mean, median, stdDev, min, max);
    }

    // Ortalama hesaplama
    private static double calculateMean(List<Double> data) {
        return data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    // Medyan hesaplama
    public static double calculateMedian(List<Double> data) {
        
        List<Double> sorted = data.stream().sorted().collect(Collectors.toList());
        int size = sorted.size();
        if (size % 2 == 0) {
            return (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
        } else {
            return sorted.get(size / 2);
        }
    }

    // Standart sapma hesaplama
    private static double calculateStandardDeviation(List<Double> data) {
        double mean = calculateMean(data);
        double variance = data.stream().mapToDouble(d -> Math.pow(d - mean, 2)).average().orElse(0.0);
        return Math.sqrt(variance);
    }
}
