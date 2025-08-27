/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pbtinss;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Arnav Singh
 */
public class PbtYatra extends javax.swing.JFrame {
 PbtVehReg dialog;
    PbtRegCheck rchk;
    ParabitDBC db1;

    /**
     * Creates new form PbtYatra
     */
    public PbtYatra() {
        initComponents();
      LiveGraph graph = new LiveGraph();
    JFXPanel chartFxPanel = graph.getGraphPanel(); // Now it's safe!
    chartFxPanel.setPreferredSize(new Dimension(graphPanel.getWidth(), graphPanel.getHeight()));


    graphPanel.setLayout(new BorderLayout());
    graphPanel.add(chartFxPanel, BorderLayout.CENTER);
    graphPanel.revalidate();
    graphPanel.repaint();
         
    try {
             
            setExtendedState(JFrame.MAXIMIZED_BOTH);
               tableLoader();
    
            String empId = PbtEmpLog.getEmpID();  // <-- Get the logged-in EmpID
            //  System.out.println("Accessed EmpID in PbtYatra: " + empId);

            String S6 = "SELECT ct.CPNo, ct.CPName, ct.LocationName, "
                    + "er.EmpId, er.EmpName ,er.CheckPointFK "
                    + "FROM checkpointtoll ct, empreg er "
                    + "WHERE ct.CPNo = er.CheckPointFK AND er.EmpId = '" + empId + "'";

        db1 = new ParabitDBC(); // initialize db1 if not already
        db1.rs = db1.stm.executeQuery(S6);

            if (db1.rs.next()) {
     String S22 = db1.rs.getString(1) + " , " + db1.rs.getString(2) + " , " + db1.rs.getString(3);
     j1.setText(S22);
            } else {
                j1.setText("No data found for EmpID: " + empId);
            }

        } catch (Exception e) {

            j1.setText("Error loading data.");
        }
    }
    private void tableLoader(){
    Thread th=new Thread((Runnable) this);
    th.start();
    }
   public void run(){
       
       LocalDate lastDate=null;
       int d1= 0,d2= 0,d3= 0,d4= 0,d0= 0,rd1= 0,rd2= 0,rd3= 0,rd4= 0,rd0= 0;
        int ad1= 0,ad2= 0,ad3= 0,ad4= 0,ad0= 0,rrd1= 0,rrd2= 0,rrd3= 0,rrd4= 0,rrd0= 0;
        
       while(true){
       try{
           
                DefaultTableModel model = (DefaultTableModel) table1.getModel();

                LocalDate curDate=LocalDate.now();
                
                if(!curDate.equals(lastDate))
                {
                    
                    //Arrival Date
                    db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ArrivalDate ='"+ curDate.minusDays(1) +"';");
                    if (db1.rs.next()) d0=db1.rs.getInt(1);
                    db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ArrivalDate ='"+(curDate)+"';");
                    if (db1.rs.next()) d1=db1.rs.getInt(1);
                    db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ArrivalDate ='"+ curDate.plusDays(1) +"';");
                    if (db1.rs.next()) d2=db1.rs.getInt(1);
                    db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ArrivalDate ='"+ curDate.plusDays(2) +"';");
                    if (db1.rs.next()) d3=db1.rs.getInt(1);
                    db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ArrivalDate ='"+ curDate.plusDays(3) +"';");
                    if (db1.rs.next()) d4=db1.rs.getInt(1);

                    // Return Date
                    db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ReturnDate ='"+ curDate.minusDays(1) +"' And Status=1;");
                    if (db1.rs.next()) rd0=db1.rs.getInt(1);
                    db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ReturnDate ='"+ curDate +"' And Status=1;");
                    if (db1.rs.next()) rd1=db1.rs.getInt(1);
                    db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ReturnDate ='"+ curDate.plusDays(1) +"' And Status=1;");
                    if (db1.rs.next()) rd2=db1.rs.getInt(1);
                    db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ReturnDate ='"+ curDate.plusDays(2) +"' And Status=1;");
                    if (db1.rs.next()) rd3=db1.rs.getInt(1);
                    db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ReturnDate ='"+ curDate.plusDays(3) +"' And Status=1;");
                    if (db1.rs.next()) rd4=db1.rs.getInt(1);
                    
                    lastDate=curDate;
                }
                
                db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ArrivalDate ='"+ curDate.minusDays(1) +"' And Status=1;");
                if (db1.rs.next()) ad0=db1.rs.getInt(1);
                db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ArrivalDate ='"+ curDate +"' And Status=1;");
                if (db1.rs.next()) ad1=db1.rs.getInt(1);
                db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ArrivalDate ='"+ curDate.plusDays(1) +"' And Status=1;");
                if (db1.rs.next()) ad2=db1.rs.getInt(1);
                db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ArrivalDate ='"+ curDate.plusDays(2) +"' And Status=1;");
                if (db1.rs.next()) ad3=db1.rs.getInt(1);
                db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ArrivalDate ='"+ curDate.plusDays(3) +"' And Status=1;");
                if (db1.rs.next()) ad4=db1.rs.getInt(1);

                db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ReturnDate ='"+ curDate.minusDays(1) +"' And Status=0;");
                if (db1.rs.next()) rrd0=db1.rs.getInt(1);
                db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ReturnDate ='"+ curDate +"' And SNo=0;");
                if (db1.rs.next()) rrd1=db1.rs.getInt(1);
                db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ReturnDate ='"+ curDate.plusDays(1) +"' And Status=0;");
                if (db1.rs.next()) rrd2=db1.rs.getInt(1);
                db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ReturnDate ='"+ curDate.plusDays(2) +"' And Status=0;");
                if (db1.rs.next()) rrd3=db1.rs.getInt(1);
                db1.rs=db1.stm.executeQuery("SELECT COUNT(*) FROM personalvehreg WHERE ReturnDate ='"+ curDate.plusDays(3) +"' And Status=0;");
                if (db1.rs.next()) rrd4=db1.rs.getInt(1);

                model.setValueAt(curDate.minusDays(1), 0, 5);
                model.setValueAt(curDate, 0, 1); 
                model.setValueAt(curDate.plusDays(1), 0, 2);  
                model.setValueAt(curDate.plusDays(2), 0, 3);  
                model.setValueAt(curDate.plusDays(3), 0, 4);

                model.setValueAt(d0, 1, 5);
                model.setValueAt(d1, 1, 1); 
                model.setValueAt(d2, 1, 2);  
                model.setValueAt(d3, 1, 3);  
                model.setValueAt(d4, 1, 4);

                model.setValueAt(ad0, 2, 5);
                model.setValueAt(ad1, 2, 1);    
                model.setValueAt(ad2, 2, 2);
                model.setValueAt(ad3, 2,3);
                model.setValueAt(ad4, 2, 4);

                model.setValueAt(d0-ad0, 3, 5);
                model.setValueAt(d1-ad1, 3, 1); 
                model.setValueAt(d2-ad2, 3, 2);  
                model.setValueAt(d3-ad3, 3, 3);  
                model.setValueAt(d4-ad4, 3, 4);

                model.setValueAt(rd0, 5, 5);
                model.setValueAt(rd1, 5, 1); 
                model.setValueAt(rd2, 5, 2);  
                model.setValueAt(rd3, 5, 3);  
                model.setValueAt(rd4, 5, 4);

                model.setValueAt(rrd0, 6, 5);
                model.setValueAt(rrd1, 6, 1);    
                model.setValueAt(rrd2, 6, 2);
                model.setValueAt(rrd3, 6,3);
                model.setValueAt(rrd4, 6, 4);

                model.setValueAt(rd0-rrd0, 7, 5);
                model.setValueAt(rd1-rrd1, 7, 1); 
                model.setValueAt(rd2-rrd2, 7, 2);  
                model.setValueAt(rd3-rrd3, 7, 3);  
                model.setValueAt(rd4-rrd4, 7, 4);
                
                Thread.sleep(200); 
                
             }
             catch(Exception e){
               System.out.print(e);
             }
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

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        j1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        cb4 = new javax.swing.JButton();
        graphPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Vehicle Registration");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Person Registration");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Registration Check");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        j1.setText("System Details");

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null, null, null, null},
                {"Arriving", null, null, null, null, null},
                {"Arrived", "", null, null, null, null},
                {"To Arrive", null, null, null, null, null},
                {"", null, null, null, null, null},
                {"Returning", null, null, null, null, null},
                {"Returned", null, null, null, null, null},
                {"To Return", null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "", "Today", "Tomorrow", "The day after tomorrow", "In Three Days", "Yesterday"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table1);

        cb4.setText("More Details");
        cb4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 574, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 471, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(321, 321, 321)
                        .addComponent(cb4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(174, 174, 174)
                                .addComponent(j1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(j1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(cb4)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int frameWidth = this.getWidth();
        int frameHeight = this.getHeight();

        //System.out.println("Frame width: " + frameWidth);  // for debugging
        dialog = new PbtVehReg(this, true);
        dialog.setSize((int) (frameWidth * 0.9), (int) (frameHeight * 0.9));
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        rchk = new PbtRegCheck(new javax.swing.JFrame(), true);
        rchk.setVisible(true);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void cb4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb4ActionPerformed
        int frameWidth = this.getWidth();
        int frameHeight = this.getHeight();

        //System.out.println("Frame width: " + frameWidth);  // for debugging
        PbtMoreDetails  dia = new PbtMoreDetails(this, true);
        dia.setSize((int) (frameWidth * 0.9), (int) (frameHeight * 0.7));
        dia.setLocationRelativeTo(this);
        dia.setVisible(true);
    }//GEN-LAST:event_cb4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(PbtYatra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PbtYatra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PbtYatra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PbtYatra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PbtYatra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cb4;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JLabel j1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table1;
    // End of variables declaration//GEN-END:variables
}
