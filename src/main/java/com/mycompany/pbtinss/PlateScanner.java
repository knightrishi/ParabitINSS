/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pbtinss;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PlateScanner {
 public   static String   ans="";

public static BufferedImage toGrayscale(BufferedImage original) {
   
    BufferedImage gray = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
    Graphics g = gray.getGraphics();
    g.drawImage(original, 0, 0, null);
  
    g.dispose();
    return gray;
}
    public static void main(String[] args) {
       QrCode ob = new QrCode();
        Webcam webcam = Webcam.getDefault();
        if (webcam == null) {
            System.out.println("No webcam detected.");
            return;
        }

        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();

       
        WebcamPanel panel = new WebcamPanel(webcam);
        JFrame window = new JFrame("Live Feed");
        window.add(panel);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("D:/Tess4J-3.4.8-src/Tess4J/tessdata"); 
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(7);
       
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000); 
                  BufferedImage image = webcam.getImage();
        if (image != null) {
     BufferedImage grayImage = toGrayscale(image);

                // Step 2: OCR with Tesseract
                String text = tesseract.doOCR(grayImage);

                 String[] lines = text.split("\\n"); 
                StringBuilder plateCandidate = new StringBuilder();

             for (String line : lines) {
                line = line.trim().replaceAll("[^A-Z0-9]", ""); // clean line
                    if (line.equalsIgnoreCase("IND") || line.contentEquals("5") ) {
                 continue; // Skip the 'IND' printed on plates
                 }

     
            if (line.length() >= 6 && line.length() <= 10) {
                plateCandidate.append(line);
            } else if (line.length() >= 3 && line.length() <= 5) {
             // Occasionally small chunks like "MH", "DL1", or letters
            plateCandidate.append(line);
            }
    }

    String   result = plateCandidate.toString();
    
    // Final cleaning (optional)
    result = result.replaceAll("[^A-Z0-9]", "").trim();
if (result.length() >= 4 && result.charAt(4) == '3') {
    StringBuilder corrected = new StringBuilder(result);
    corrected.setCharAt(3, 'B');
    result = corrected.toString();
    
}
ans=result;
    // Show result if it looks like a plate
    if (!result.isEmpty() && result.length() >= 8 && result.length() <= 12) {
        JOptionPane.showMessageDialog(null, "Detected Plate: " + result);
        System.out.println("Detected Plate: " + result);
          ob.promptAndGenerateQR(); // ✅ QR code generation triggered
    }
}

                } catch (TesseractException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            webcam.close();
        }).start();
    }
}