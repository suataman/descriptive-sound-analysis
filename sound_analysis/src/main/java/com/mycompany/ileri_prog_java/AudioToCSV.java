package com.mycompany.ileri_prog_java;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

public class AudioToCSV {
    public static void main(String[] args) {
        String audioFilePath = "C:\\Users\\ASUS\\Downloads\\a.wav"; // Ses dosyasının yolu
        String csvFilePath = "C:\\Users\\ASUS\\Downloads\\a_output.csv"; // Çıktı CSV dosyasının yolu

        try {
            // Ses dosyasını açma
            File audioFile = new File(audioFilePath);
            String fileName = audioFile.getName();

            // Dosya adından harfi çıkarma (örneğin: "a.wav" -> "a")
            String letter = fileName.substring(0, fileName.indexOf('.')).toUpperCase();

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            int frameSize = format.getFrameSize();
            float frameRate = format.getFrameRate();

            // Ses verilerini okumak
            byte[] audioData = audioStream.readAllBytes();
            List<String> csvLines = new ArrayList<>();

            // CSV başlıkları
            csvLines.add("Timestamp,Amplitude,Letter");

            // Her örnek için genlik değerini çıkarma
            for (int i = 0; i < audioData.length; i += frameSize) {
                if (i + 1 >= audioData.length) break; // Veri dışına çıkmayı önlemek için

                // Ses verisinin genlik değerini al
                short amplitude = (short) ((audioData[i] & 0xFF) | (audioData[i + 1] << 8));

                // Timestamp'i hesapla
                float timestamp = i / frameRate;

                // CSV formatında veriyi ekle
                csvLines.add(String.format("%.6f,%f,%s", timestamp, amplitude / 32768.0, letter));
            }

            // CSV dosyasına yazma
            BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath));
            for (String line : csvLines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();

            System.out.println("CSV dosyası başarıyla kaydedildi: " + csvFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
