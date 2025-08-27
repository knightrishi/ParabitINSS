/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXSwingMain.java to edit this template
 */
package com.mycompany.pbtinss;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Arnav Singh
 */
public class LiveGraph{
    
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private JFXPanel fxContainer;
    private LineChart<String, Number> lineChart;
    private XYChart.Series<String, Number> apAcSeries;
    private XYChart.Series<String, Number> stdLineSeries;

    public JFXPanel getGraphPanel() {
    fxContainer = new JFXPanel();
    fxContainer.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    Platform.runLater(this::createScene);
    return fxContainer;
}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Live Vehicle Count Graph");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            LiveGraph app = new LiveGraph();
            app.initUI(frame);

            frame.setSize(WIDTH, HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private void initUI(JFrame frame) {
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.add(fxContainer, BorderLayout.CENTER);

        Platform.runLater(this::createScene);
    }

    private void createScene() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Hour");

        NumberAxis yAxis = new NumberAxis(0, 15000, 600);
        yAxis.setLabel("Vehicle Count");

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Live Vehicle Count (AP + AC)");

        apAcSeries = new XYChart.Series<>();
        apAcSeries.setName("AP + AC");

        stdLineSeries = new XYChart.Series<>();
        stdLineSeries.setName("Standard Line");

        lineChart.getData().addAll(apAcSeries, stdLineSeries);

        StackPane root = new StackPane(lineChart);
        
       fxContainer.setScene(new Scene(root));

        startAutoRefresh();
        
        
    }

    private void startAutoRefresh() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    try {
                        updateChart();
                    } catch (SQLException ex) {
                        Logger.getLogger(LiveGraph.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        }, 0, 5000); // refresh every 5 seconds
    }

    private void updateChart() throws SQLException {
        Map<String, Integer> hourMap = new TreeMap<>();
        ParabitDBC db = new ParabitDBC();

        String query = "SELECT Time, APCount, ACCount FROM tollpassedveh WHERE Date = CURDATE()";
        db.rs = db.getStatement().executeQuery(query);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        while (db.rs.next()) {
            String hourStr = db.rs.getString("Time");
            if (hourStr == null || hourStr.trim().isEmpty()) continue;
            
            int hour;
            try {
                hour = Integer.parseInt(hourStr.trim());
            } catch (NumberFormatException e) {
                continue;
            }
            
            if (hour < 0 || hour > 23) continue;
            
            LocalTime time = LocalTime.of(hour, 0);
            String formattedHour = time.format(formatter);
            
            int total = db.rs.getInt("APCount") + db.rs.getInt("ACCount");
            hourMap.merge(formattedHour, total, Integer::sum);
        }
        db.close();

      apAcSeries.getData().clear();
stdLineSeries.getData().clear();

int hourIndex = 0;
for (Map.Entry<String, Integer> entry : hourMap.entrySet()) {
    String hour = entry.getKey();
    int total = entry.getValue();

    apAcSeries.getData().add(new XYChart.Data<>(hour, total));
    stdLineSeries.getData().add(new XYChart.Data<>(hour, (hourIndex + 1) * 600)); // Diagonal Line
    hourIndex++;
}
    }
}
