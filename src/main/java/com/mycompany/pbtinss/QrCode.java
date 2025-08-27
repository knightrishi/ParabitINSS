/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pbtinss;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Arnav Singh
 */
public class QrCode {
    //public PlateScannerOnly obj;
    public static void main(String[] args) {
        new QrCode().promptAndGenerateQR();
    }

    public  void promptAndGenerateQR() {
       // String vno = JOptionPane.showInputDialog(null, "Enter Vehicle Number:", "QR Code Generator", JOptionPane.QUESTION_MESSAGE);
        String vno=PlateScanner.ans;
        if (vno == null || vno.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vehicle number is required to generate QR code.");
            return;
        }

        ParabitDBC db = new ParabitDBC(); // Use your DB helper class

        try {
            String data = fetchVehicleData(db, vno);

            if (data == null) {
                JOptionPane.showMessageDialog(null, "No vehicle found with number: " + vno);
                return;
            }

            // QR Code generation
            int width = 500;
            int height = 500;
            String format = "jpg";
            String path = "C:\\Users\\arnav\\Desktop\\qrcode_" + vno + ".jpg";

            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
            Path outputPath = Paths.get(path);
            MatrixToImageWriter.writeToPath(matrix, format, outputPath);

            // Display in JFrame
            JFrame frame = new JFrame("QR Code for VNo: " + vno);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(520, 520);
            frame.add(new JLabel(new ImageIcon(image)));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            System.out.println("QR Code generated at: " + path);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error generating QR Code: " + e.getMessage());
        } finally {
            db.close(); // Clean up
        }
    }

    private String fetchVehicleData(ParabitDBC db, String vno) {
        String query = "SELECT VNo, OwnerName, OwnerMob, EmergencyMob,ArrivalDate, ReturnDate FROM personalvehreg WHERE VNo = ?";
        try {
            db.ps = db.getConnection().prepareStatement(query);
            db.ps.setString(1, vno);
            db.rs = db.ps.executeQuery();

            if (db.rs.next()) {
                return db.rs.getString("VNo") + "," +
                       db.rs.getString("OwnerName") + ", " +
                       db.rs.getString("OwnerMob") + ", " +
                       db.rs.getString("EmergencyMob") + ", " +
                     //  db.rs.getInt("VCategoryFK ") + ", " +
                       db.rs.getDate("ArrivalDate") + ", " +
                       db.rs.getDate("ReturnDate");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

