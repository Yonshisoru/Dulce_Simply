/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dulce.simply;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

/**
 *
 * @author Yonshisoru
 */
public class MySQLConnect {
    Connection conn = null;
    public static Connection ConnectDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dulce","root","");
            return conn;
        }catch(Exception e)
        {
             JOptionPane.showMessageDialog(null, "Status:Not connected","System",ERROR_MESSAGE);
             JOptionPane.showMessageDialog(null, "Auto Execute Software!!","System",PLAIN_MESSAGE);
             System.exit(0);
            return null;
        }
    }
}
