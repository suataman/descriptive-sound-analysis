/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ileri_prog_java;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AmplitudeExtractor {

    public static List<Double> extractAmplitudes(String audioFilePath) throws UnsupportedAudioFileException, IOException {

        // Ses dosyasını açma
        File audioFile = new File(audioFilePath);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat format = audioStream.getFormat();
        int frameSize = format.getFrameSize();

        // Ses verilerini okuma
        byte[] audioData = audioStream.readAllBytes();
        List<Double> amplitudes = new ArrayList<>();

        // Her örnek için genlik değerlerini çıkarma
        for (int i = 0; i < audioData.length; i += frameSize) {
            short amplitude = (short) ((audioData[i] & 0xFF) | (audioData[i + 1] << 8));
            amplitudes.add(amplitude / 32768.0); // Normalleştirilmiş genlik
        }

        return amplitudes;
    }
}
