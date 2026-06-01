/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pbtinss;

import com.mycompany.pbtinss.ParabitDBC;
import java.awt.Component;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author anama
 */
public class PBTTollDirector extends javax.swing.JFrame {

    /**
     * Creates new form TollDirector
     */
        public PBTTollDirector() {
        initComponents();

        
                DefaultTableModel model = new DefaultTableModel(
        new Object[][]{},
        new String[]{"Toll Name", "Toll ID", "Alert", "Graph"}
    ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 2) return JProgressBar.class ; 
            if (columnIndex == 3) return JButton.class;               // QR button column
            return Object.class;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            // Only QR button column editable (so button is clickable)
            return column == 3;
        }
    };

    t1.setModel(model);
t1.setModel(model);

// Use your inner classes directly
t1.getColumnModel().getColumn(2).setCellRenderer(new ProgressRenderer());
t1.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
t1.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));



        ParabitDBC db1 = new ParabitDBC();
        try {
            db1.rs = db1.stm.executeQuery("Select LocationName,CpName from checkpointtoll;");
            
            while(db1.rs.next()){
            
                model.addRow(new Object[]{db1.rs.getString(1), db1.rs.getString(2), 0, "Graph"});
            
            }
            
            
        } 
        catch (Exception e) {
         
            e.printStackTrace();

        }
                
                
                
            t1.setRowHeight(23);

        
        
        
        
        
        
        // Create JFXPanel
        JFXPanel jfxPanel = new JFXPanel();
        p1.setLayout(new java.awt.BorderLayout());
        p1.add(jfxPanel, java.awt.BorderLayout.CENTER);


        setSize(800, 600); // Ensure window is large enough
        setLocationRelativeTo(null); // Center window

        // Initialize JavaFX chart on JavaFX thread
        Platform.runLater(() -> {
            // X Axis
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Date");

            // Y Axis
            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("No of Vehicle");

            // BarChart
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Graphical Representation of Veh Arrival ");
            LocalDate today = LocalDate.now();
            ParabitDBC db = new ParabitDBC();
            int a = 0;
            try {
                db.rs = db.stm.executeQuery("SELECT COUNT(ArrivalDate) FROM personalvehreg where ArrivalDate = '2025-10-12' ;");
                if (db.rs.next()) {  // ✅ Move cursor to the first row
        a = db.rs.getInt(1);
    }
                db.con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PBTTollDirector.class.getName()).log(Level.SEVERE, null, ex);
            }
                        ParabitDBC db2 = new ParabitDBC();

                        int b = 0;
            try {
                db2.rs = db2.stm.executeQuery("SELECT COUNT(ArrivalDate) FROM personalvehreg where ArrivalDate = '2025-10-11' ;");
                if (db2.rs.next()) {  // ✅ same fix
        b = db2.rs.getInt(1);
    }
                                db2.con.close();

            } catch (SQLException ex) {
                Logger.getLogger(PBTTollDirector.class.getName()).log(Level.SEVERE, null, ex);
            }    
                                    ParabitDBC db3 = new ParabitDBC();

            int c = 0;
            try {
                db3.rs = db3.stm.executeQuery("SELECT COUNT(ArrivalDate) FROM personalvehreg where ArrivalDate = '2025-10-10' ;");
                 if (db3.rs.next()) {  // ✅ same fix
        c = db3.rs.getInt(1);
    }
                                db3.con.close();

            } catch (SQLException ex) {
                Logger.getLogger(PBTTollDirector.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Vec Count ");
            System.out.print(a+ "  "+ b+" "+c);
            series.getData().add(new XYChart.Data<>(""+today, a));
            series.getData().add(new XYChart.Data<>(""+today.plusDays(1), b));
            series.getData().add(new XYChart.Data<>(""+today.plusDays(2), c));

            barChart.getData().add(series);

            // 🔹 THIS IS IMPORTANT: Put BarChart into a Scene and attach to JFXPanel
            Scene scene = new Scene(barChart, 330, 220);
            jfxPanel.setScene(scene);
        });
    }
        
        
        
          class ProgressRenderer extends JProgressBar implements TableCellRenderer {
        public ProgressRenderer() {
            super(0, 100);
            setStringPainted(true);
        }
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setValue((int) value);
            return this;
        }
    }

    // Button ko  renderer kiya hai
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Button" : value.toString());
            return this;
        }
    }

    // Button  ka editor
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "Button" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                JOptionPane.showMessageDialog(button, "this is the ggraph of  the toll" + (t1.getValueAt(t1.getSelectedRow(), 0)));
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
    
    
    
    
        
        
        
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        p1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout p1Layout = new javax.swing.GroupLayout(p1);
        p1.setLayout(p1Layout);
        p1Layout.setHorizontalGroup(
            p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 616, Short.MAX_VALUE)
        );
        p1Layout.setVerticalGroup(
            p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );

        t1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Toll Name", "Toll ID", "Alert", "Graph"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(t1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(190, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(283, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PBTTollDirector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PBTTollDirector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PBTTollDirector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PBTTollDirector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PBTTollDirector().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel p1;
    private javax.swing.JTable t1;
    // End of variables declaration//GEN-END:variables
}
