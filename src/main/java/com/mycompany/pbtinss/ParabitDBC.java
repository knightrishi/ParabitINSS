/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pbtinss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Arnav Singh
 */
public class ParabitDBC {
     public Connection con;
    public Statement stm;
    public PreparedStatement ps;
    public ResultSet rs;

    public ParabitDBC() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/parabitinss", "root", "");
            stm = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
  public Connection getConnection() {
        return con;
    }

    public Statement getStatement() {
        return stm;
    }
    public void close() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   public static void main(String ar[]){
  ParabitDBC obj=new ParabitDBC();
  
   }

}
