/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufra.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Frank
 */
public class BaseDAO {
    public BaseDAO(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    protected Connection getConnection() throws SQLException{
        String url = "jdbc:mysql://localhost/olimpiada";
        
        Connection conn = DriverManager.getConnection(url,"root","");
        
        return conn;
    }
    
    public static void main(String[] args) throws SQLException {
        BaseDAO db = new BaseDAO();
        Connection conn = db.getConnection();
        System.out.println(conn);
    }
}
