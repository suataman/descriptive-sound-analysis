/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ileri_prog_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

    // Veritabanı bağlantısı bilgileri
    private static final String URL = "jdbc:mysql://localhost:3306/sound";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Bağlantıyı oluştur
    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Analiz sonuçlarını veritabanına ekle
    public static void insertAnalysisResult(String letter, DescriptiveAnalysis.AnalysisResult result) {
        String query = "INSERT INTO analysis_results (letter, mean, median, std_dev, min, max) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Parametreleri ayarla
            pstmt.setString(1, letter);
            pstmt.setDouble(2, result.mean);
            pstmt.setDouble(3, result.median);
            pstmt.setDouble(4, result.stdDev);
            pstmt.setDouble(5, result.min);
            pstmt.setDouble(6, result.max);

            // Sorguyu çalıştır
            pstmt.executeUpdate();
            System.out.println("Analiz sonuçları başarıyla kaydedildi!");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Veritabanına ekleme sırasında hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
