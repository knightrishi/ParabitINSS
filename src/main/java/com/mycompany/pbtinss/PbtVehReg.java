/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.pbtinss;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Arnav Singh
 */
public class PbtVehReg extends javax.swing.JDialog {
 String s1, s2, s3, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s16, s19, s20, s21, s22, s23, s24, s;
    ParabitDBC dbc;
    String c1, c2, c3, c4, c5, C, vc, vehtype, driv, d1, d2, ft, ft1, ft2;
    private File selectedImage;
    String imagepath;
    FileInputStream fips;
    private String vehno;
    byte[] imageData;
    // private static String EmpID;
//String query = "INSERT INTO personalvehreg (Vimg) VALUES (?)";
 
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImage = fileChooser.getSelectedFile();

            try (FileInputStream fips = new FileInputStream(selectedImage)) {
                imageData = new byte[(int) selectedImage.length()];
                fips.read(imageData);
                System.out.println("Image loaded successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage());
            }
        }
    }
    /**
     * Creates new form PbtVehReg
     */
    public PbtVehReg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        dbc = new ParabitDBC();
        j24.setVisible(false);
        tf26.setVisible(false);
          cr7.setVisible(false);
        cr6.setVisible(false);
       // vno=tf3.getText().trim();
        C = "SELECT * FROM state;";
        cb1.removeAllItems();
        cb2.removeAllItems();

        try {
            dbc.rs = dbc.stm.executeQuery(C);
            while (dbc.rs.next()) {
                int code = dbc.rs.getInt("StateCode");
                c1 = dbc.rs.getString("StateName");
                c2 = dbc.rs.getString("StateAbbreviation");

                c3 = code + "," + c1 + "," + c2;
                cb1.addItem(c3);
                cb2.addItem(c3);

            }
        } catch (SQLException ex) {
            System.out.print("-------------------------->>>>>>>>>>>>>>>>>>> " + ex);
        }

        ft = "SELECT * FROM fueltype;";
        cb5.removeAllItems();

        try {
            dbc.rs = dbc.stm.executeQuery(ft);
            while (dbc.rs.next()) {

                ft1 = dbc.rs.getString("FType");

                cb5.addItem(ft1);

            }
        } catch (SQLException ex) {
            System.out.print("-------------------------->>>>>>>>>>>>>>>>>>> " + ex);
        }

        vehCat();

        jLabel29.setVisible(false);
        tf1.setVisible(false);

    }
 private void vehCat() {

        vc = "SELECT * FROM personalvehtype;";
        cb3.removeAllItems();

        try {
            dbc.rs = dbc.stm.executeQuery(vc);
            while (dbc.rs.next()) {

                vehtype = dbc.rs.getString("VecType");

                cb3.addItem(vehtype);

            }
        } catch (SQLException ex) {
            System.out.print("-------------------------->>>>>>>>>>>>>>>>>>> " + ex);
        }
    }
 
 
 public PbtVehReg(java.awt.Frame parent, boolean modal, String ls) {
        super(parent, modal);
        initComponents();
        cr7.setVisible(true);
        cr6.setVisible(true);
        dbc = new ParabitDBC();
this.vehno=ls;
        jButton3.setVisible(false);
        cb1.removeAllItems();
        cb2.removeAllItems();
        cb3.removeAllItems();
        cb5.removeAllItems();
        b4.setVisible(false);

        String query = "SELECT * FROM personalvehreg WHERE VNo = '" + ls + "'";
        try {
            dbc.rs = dbc.stm.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(PbtVehReg.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (dbc.rs.next()) {

                try {
                    tf1.setText(dbc.rs.getString("SNo"));
                    tf2.setText(dbc.rs.getString("VRegNo"));
                    tf3.setText(dbc.rs.getString("VNo"));
                    tf5.setText(dbc.rs.getString("VName"));
                    tf6.setText(dbc.rs.getString("RTORegNO"));
                    tf7.setText(dbc.rs.getString("OwnerName"));
                    tf8.setText(dbc.rs.getString("OwnerMob"));
                    tf9.setText(dbc.rs.getString("EmergencyName"));

                    tf10.setText(dbc.rs.getString("EmergencyMob"));
                    tf11.setText(dbc.rs.getString("DrivedBy"));

                    cb1.addItem("" + dbc.rs.getInt("FromStateFK"));
                    cb2.addItem("" + dbc.rs.getInt("ToStateFK"));
                    cb3.addItem("" + dbc.rs.getInt("VCategoryFK"));
                    cb5.addItem("" + dbc.rs.getInt("FuelTypeFK"));

                    tf12.setText(dbc.rs.getString("DriverName"));
                    tf13.setText(dbc.rs.getString("DriverMob"));
                    tf14.setText(dbc.rs.getString("VIP"));
                    tf16.setText(dbc.rs.getString("FromCity"));
                    tf19.setText(dbc.rs.getString("ToCity"));
                   // tf20.setText(dbc.rs.getString("ArrivalDate"));
                   
                   // tf21.setText(dbc.rs.getString("ReturnDate"));
                   jc1.setDate(dbc.rs.getDate("ArrivalDate"));
                    jc2.setDate(dbc.rs.getDate("ReturnDate"));
                    tf22.setText(dbc.rs.getString("ApproxDay"));
                    tf23.setText(dbc.rs.getString("OtherDest"));
                    tf24.setText(dbc.rs.getString("NoOfPassenger"));
                    tf26.setText(dbc.rs.getString("RegisteredByEmp"));
                    imageData = dbc.rs.getBytes("Vimg");
                    jButton4.setVisible(false);

                    if (imageData != null) {
                        ImageIcon icon = new ImageIcon(imageData);
                        jLabel25.setText("");
                        jLabel25.setIcon(icon);
                    } else {
                        jLabel25.setIcon(null); // or a placeholder icon
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PbtVehReg.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(PbtVehReg.class.getName()).log(Level.SEVERE, null, ex);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        tff = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        tf1 = new javax.swing.JTextField();
        tf2 = new javax.swing.JTextField();
        tf3 = new javax.swing.JTextField();
        tf5 = new javax.swing.JTextField();
        tf6 = new javax.swing.JTextField();
        tf7 = new javax.swing.JTextField();
        tf8 = new javax.swing.JTextField();
        tf9 = new javax.swing.JTextField();
        tf10 = new javax.swing.JTextField();
        tf12 = new javax.swing.JTextField();
        tf13 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        b4 = new javax.swing.JButton();
        tf22 = new javax.swing.JTextField();
        tf23 = new javax.swing.JTextField();
        tf24 = new javax.swing.JTextField();
        tf16 = new javax.swing.JTextField();
        tf19 = new javax.swing.JTextField();
        cb1 = new javax.swing.JComboBox<>();
        cb2 = new javax.swing.JComboBox<>();
        cb3 = new javax.swing.JComboBox<>();
        cb5 = new javax.swing.JComboBox<>();
        tf14 = new javax.swing.JTextField();
        tf11 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        j24 = new javax.swing.JLabel();
        tf26 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        cb6 = new javax.swing.JComboBox<>();
        cr7 = new javax.swing.JButton();
        cr6 = new javax.swing.JButton();
        jc1 = new com.toedter.calendar.JDateChooser();
        jc2 = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Parabit Technology INSS");

        jTabbedPane1.setBackground(new java.awt.Color(255, 51, 0));
        jTabbedPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));

        tff.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                tffComponentAdded(evt);
            }
        });

        jLabel29.setText("Serial No.");

        jLabel30.setText("Vehicle Registration No.");

        jLabel31.setText("Vehicle Number");

        jLabel32.setText("Vehicle Category");

        jLabel33.setText("Vehicle Name");

        jLabel34.setText("RTO Registraton Number");

        jLabel35.setText("Owner Name");

        jLabel36.setText("Owner Mobile Number");

        jLabel37.setText("Emergency Name");

        jLabel38.setText("Emergency Number");

        jLabel39.setText("Drived By");

        jLabel40.setText("Driver Name");

        jLabel41.setText("Driver Mobile Number");

        jLabel42.setText("VIP");

        tf1.setText("jTextField1");
        tf1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf1ActionPerformed(evt);
            }
        });

        tf2.setText("jTextField2");
        tf2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf2ActionPerformed(evt);
            }
        });

        tf3.setText("jTextField3");

        tf5.setText("jTextField5");

        tf6.setText("jTextField6");

        tf7.setText("jTextField7");

        tf8.setText("jTextField8");

        tf9.setText("jTextField9");

        tf10.setText("jTextField10");

        tf12.setText("jTextField12");

        tf13.setText("jTextField13");

        jLabel14.setText("Fuel Type");

        jLabel15.setText("From City");

        jLabel16.setText("From State");

        jLabel17.setText("To State");

        jLabel18.setText("To City");

        jLabel19.setText("Arrival Date");

        jLabel20.setText("Return Date");

        jLabel21.setText("Approx Date");

        jLabel22.setText("Other Destinations");

        jLabel23.setText("No.Of Passsangers");

        b4.setText("RESET");
        b4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b4ActionPerformed(evt);
            }
        });

        tf22.setText("jTextField19");

        tf23.setText("jTextField20");

        tf24.setText("jTextField21");
        tf24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf24ActionPerformed(evt);
            }
        });

        tf16.setText("jTextField1");

        tf19.setText("jTextField4");

        cb1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        cb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb1ActionPerformed(evt);
            }
        });

        cb2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));

        cb3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select " }));

        cb5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", " " }));

        tf14.setText("jTextField1");

        tf11.setText("jTextField1");

        jLabel25.setText("jLabel25");

        jButton4.setText("Select Image");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        j24.setText("RegesteredByEmp");

        tf26.setText("jTextField1");
        tf26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf26ActionPerformed(evt);
            }
        });

        jButton3.setText("SUBMIT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        cb6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Toll Location" }));
        cb6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb6ActionPerformed(evt);
            }
        });

        cr7.setText("PASS");
        cr7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cr7ActionPerformed(evt);
            }
        });

        cr6.setText("UPDATE");

        javax.swing.GroupLayout tffLayout = new javax.swing.GroupLayout(tff);
        tff.setLayout(tffLayout);
        tffLayout.setHorizontalGroup(
            tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tffLayout.createSequentialGroup()
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tffLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf2)
                            .addComponent(tf3)
                            .addComponent(tf5)
                            .addComponent(tf6)
                            .addComponent(tf7)
                            .addComponent(tf8)
                            .addComponent(tf9)
                            .addComponent(tf10, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                            .addComponent(tf12)
                            .addComponent(tf13)
                            .addComponent(tf1)
                            .addComponent(cb3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tf14)
                            .addComponent(tf11)))
                    .addGroup(tffLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 228, Short.MAX_VALUE)))
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tffLayout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(tffLayout.createSequentialGroup()
                                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(j24, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton4))
                                .addGap(0, 81, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tffLayout.createSequentialGroup()
                                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tf22, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tf23, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tf24, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tf16, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tf19, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cb5, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cb1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(tffLayout.createSequentialGroup()
                                        .addGap(0, 2, Short.MAX_VALUE)
                                        .addComponent(cb2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(86, 86, 86)
                                .addComponent(cb6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))
                            .addGroup(tffLayout.createSequentialGroup()
                                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tf26, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jc1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jc2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(tffLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cr6, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125)
                        .addComponent(cr7, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        tffLayout.setVerticalGroup(
            tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tffLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cb5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tf1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tf2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tf16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(cb1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cb6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cb3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cb2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tf5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tf19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tf6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                    .addComponent(jc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tf22, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tf23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tf24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tffLayout.createSequentialGroup()
                        .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tffLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tf11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tf12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(tffLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(tffLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(j24))))
                .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tffLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(146, 146, 146))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tffLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cr7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cr6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(136, 136, 136))))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(tff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 184, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tff, javax.swing.GroupLayout.PREFERRED_SIZE, 769, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Personal Vehicle Registration", jPanel2);

        jButton1.setText("jButton1");

        jButton2.setText("jButton2");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jButton2))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jButton1)))
                .addContainerGap(1352, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jButton1)
                .addGap(32, 32, 32)
                .addComponent(jButton2)
                .addContainerGap(647, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Heavy Duty Commercial Vehicle", jPanel4);

        jPanel1.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jPanel1ComponentAdded(evt);
            }
        });

        jLabel1.setText("Serial No.");

        jLabel2.setText("Vehicle Registration No.");

        jLabel3.setText("Vehicle Number");

        jLabel4.setText("Vehicle Category");

        jLabel5.setText("Vehicle Name");

        jLabel6.setText("RTO Registraton Number");

        jLabel7.setText("Owner Name");

        jLabel8.setText("Owner Mobile Number");

        jLabel9.setText("Emergency Name");

        jLabel10.setText("Emergency Number");

        jLabel11.setText("Drived By");

        jLabel12.setText("Driver Name");

        jLabel13.setText("Driver Mobile Number");

        jLabel27.setText("VIP");

        jLabel28.setText("Fuel Type");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(1325, 1325, 1325))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Commercial Vehicle Registration", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tf1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf1ActionPerformed

    private void tf2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf2ActionPerformed

    private void b4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b4ActionPerformed

    private void tf24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf24ActionPerformed

    private void cb1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb1ActionPerformed

        if(cb1.getSelectedIndex()==22){
            String s1="MP";
            String ss="Select * From checkpointtoll where CpName LIKE '%"+s1+"%';";
            try {
                dbc.rs=dbc.stm.executeQuery(ss);
            } catch (SQLException ex) {
                Logger.getLogger(PbtVehReg.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Fetched Succesfully");
            try {
                while(dbc.rs.next()){
                    cb6.addItem((String)dbc.rs.getString("CpName"));
                    //cb6.addItem((String) dbc.rs.getString("CPName"));
                }            } catch (SQLException ex) {
                    Logger.getLogger(PbtVehReg.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            
    }//GEN-LAST:event_cb1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Choose file
        chooseImage();
        if (selectedImage != null) {
            ImageIcon icon = new ImageIcon(new ImageIcon(selectedImage.getAbsolutePath()).getImage().getScaledInstance(350, 150, Image.SCALE_SMOOTH));
            jLabel25.setIcon(icon);
            jLabel25.setText(""); // Remove the "Image Holder" text
        } else {
            JOptionPane.showMessageDialog(this, "Please select an image first!");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tf26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf26ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        /*Date df = (Date) jc1.getDate();
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        String d1 = sdfDate.format(df);

        Date df2 = (Date) jc2.getDate();
        //SimpleDateFormat sdfDate2 = new SimpleDateFormat("dd-MM-yyyy");
        String d2 = sdfDate.format(df2);*/
        // Collecting form data

        java.util.Date arrivalDate = jc1.getDate();
        java.util.Date returnDate  = jc2.getDate();

        java.sql.Date d1 = new java.sql.Date(returnDate.getTime());
        java.sql.Date d2 = new java.sql.Date(arrivalDate.getTime());
        s2 = tf2.getText();
        s3 = tf3.getText();
        s5 = tf5.getText();
        s6 = tf6.getText();
        s7 = tf7.getText();
        s8 = tf8.getText();
        s9 = tf9.getText();
        s10 = tf10.getText();
        s11 = tf11.getText();
        s12 = tf12.getText();
        s13 = tf13.getText();
        s14 = tf14.getText();
        s16 = tf16.getText();
        s19 = tf19.getText();
        s20 = tf19.getText();
        //s21 = tf21.getText();
        s22 = tf22.getText();
        s23 = tf23.getText();
        s24 = tf24.getText();

        int s17 = cb1.getSelectedIndex() + 1;
        int s18 = cb2.getSelectedIndex() + 1;
        int s4 = cb3.getSelectedIndex() + 1;
        int s15 = cb5.getSelectedIndex() + 1;      //s2     s3    s4         s5    s6          tf7        s8         s9           s10            s12          s13          s11 s14    s15        s16          s17        s18         s19      d1           d2            s22      s23        s24
        String empID = PbtEmpLog.getEmpID();      // 1      2     3          4       5         6           7           8           9               10          11          12   13     14          15          16          17         18       19          20          21          22          23          24      25
        String sql = "INSERT INTO personalvehreg (VRegNo, VNo, VCategoryFK, VName, RTORegNo, OwnerName, OwnerMob, EmergencyName, EmergencyMob, DriverName, DriverMob, DrivedBy, VIP, FuelTypeFK, FromCity, FromStateFK, ToStateFK, ToCity, ArrivalDate, ReturnDate, ApproxDay, OtherDest, NoOfPassenger, Vimg, RegisteredByEmp) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try {
            PreparedStatement ps = dbc.con.prepareStatement(sql);
            ps.setString(1, s2);
            ps.setString(2, s3);
            ps.setInt(3, s4);
            ps.setString(4, s5);
            ps.setString(5, s6);
            ps.setString(6, s7);
            ps.setString(7, s8);
            ps.setString(8, s9);
            ps.setString(9, s10);
            ps.setString(10, s12);
            ps.setString(11, s13);
            ps.setString(12, s11);
            ps.setString(13, s14);
            ps.setInt(14, s15);

            ps.setString(15, s16);
            ps.setInt(16, s17);
            ps.setInt(17, s18);
            ps.setString(18, s19);
            ps.setDate(19, d2);
            ps.setDate(20,d1);
            ps.setString(21, s22);

            /* if (jc1 != null && jc2 != null && jc1.getDate() != null && jc2.getDate() != null) {
                java.util.Date arrivalDate = jc1.getDate();
                java.util.Date returnDate  = jc2.getDate();

                java.sql.Date sqlRetDate = new java.sql.Date(returnDate.getTime());
                java.sql.Date sqlArrDate = new java.sql.Date(arrivalDate.getTime());

                ps.setDate(20, sqlArrDate);
                ps.setDate(21, sqlRetDate);
            } else {
                ps.setNull(20, java.sql.Types.DATE);
                ps.setNull(21, java.sql.Types.DATE);
            }*/

            ps.setString(22, s23);
            ps.setString(23, s24);
            ps.setBytes(24, imageData); // image data
            ps.setString(25, empID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Successfully Inserted");
                this.dispose();
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Insertion Failed");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            Logger.getLogger(PbtVehReg.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void cb6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb6ActionPerformed

    }//GEN-LAST:event_cb6ActionPerformed
/*public String vehicleNumber() {
   JOptionPane.showMessageDialog(null,vno);
    return vno;  // NOT jTextField3.toString()
}*/

    
    private void cr7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cr7ActionPerformed
        // TODO add your handling code here:
        PbtForEventVeh vh= new PbtForEventVeh(new javax.swing.JFrame(), true);
         vh.setVisible(true);
        LocalTime curTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String sqlTime = curTime.format(formatter);
     
        String s2 = "SELECT * FROM personalvehreg WHERE VNo = '" + vehno + "' AND Status = 1;";
        try {
            dbc.rs = dbc.stm.executeQuery(s2);

            if (dbc.rs.next()) {  // Always move to the first row first
                int status = dbc.rs.getInt("Status");

                if (status == 1) {
                    // Vehicle is leaving - mark as returned
                    String s1 = "UPDATE personalvehreg SET Status = 0, CheckoutTime = '" + sqlTime + "' WHERE VNo = '" + vehno + "';";
                    dbc.stm.executeUpdate(s1);
                    System.out.println("Status Returned");
                    JOptionPane.showMessageDialog(this, " Vechicle Returned");
                   // JOptionPane.showMessageDialog(this, "Vehicle Returned");
this.dispose();  // Close the current window

                } else if (status == -1) {
                    // Vehicle is entering - mark as arrived
                    
                    String s1 = "UPDATE personalvehreg SET Status = 1, CheckinTime = '" + sqlTime + "' WHERE VNo = '" + vehno + "';";
                    dbc.stm.executeUpdate(s1);
                    System.out.println("Status Arrived");
                    JOptionPane.showMessageDialog(this, "Arrived");
                } else {
                    System.out.println("Vehicle Not Verified");
                    JOptionPane.showMessageDialog(this, "Vehicle Not Verified");
                }

            } else {
                System.out.println("Vehicle Not Registered");
                JOptionPane.showMessageDialog(this, "Vehicle Not Registered");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_cr7ActionPerformed

    private void tffComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tffComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tffComponentAdded

    private void jPanel1ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jPanel1ComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1ComponentAdded

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
            java.util.logging.Logger.getLogger(PbtVehReg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PbtVehReg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PbtVehReg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PbtVehReg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PbtVehReg dialog = new PbtVehReg(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b4;
    private javax.swing.JComboBox<String> cb1;
    private javax.swing.JComboBox<String> cb2;
    private javax.swing.JComboBox<String> cb3;
    private javax.swing.JComboBox<String> cb5;
    private javax.swing.JComboBox<String> cb6;
    private javax.swing.JButton cr6;
    private javax.swing.JButton cr7;
    private javax.swing.JLabel j24;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jc1;
    private com.toedter.calendar.JDateChooser jc2;
    private javax.swing.JTextField tf1;
    private javax.swing.JTextField tf10;
    private javax.swing.JTextField tf11;
    private javax.swing.JTextField tf12;
    private javax.swing.JTextField tf13;
    private javax.swing.JTextField tf14;
    private javax.swing.JTextField tf16;
    private javax.swing.JTextField tf19;
    private javax.swing.JTextField tf2;
    private javax.swing.JTextField tf22;
    private javax.swing.JTextField tf23;
    private javax.swing.JTextField tf24;
    private javax.swing.JTextField tf26;
    private javax.swing.JTextField tf3;
    private javax.swing.JTextField tf5;
    private javax.swing.JTextField tf6;
    private javax.swing.JTextField tf7;
    private javax.swing.JTextField tf8;
    private javax.swing.JTextField tf9;
    private javax.swing.JPanel tff;
    // End of variables declaration//GEN-END:variables
}
