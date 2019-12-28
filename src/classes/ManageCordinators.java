/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hansani Perera
 */
public class ManageCordinators extends ManageUser{
    
     private  PreparedStatement pst,pst1;
     private int count = 0;
    private  ResultSet rst, rst1;
    
    //add new cordinator
    public void addUser(int id,int co) throws SQLException{              
        String userInfo = "insert into cordinators values(?, ?)";
        pst1 = Db.connection().prepareStatement(userInfo);
        pst1.setInt(1,id);
        pst1.setInt(2, co);
       
                       
        pst1.executeUpdate();          
        System.out.println("INSERT COMPLETE");
    }
    
    //get total cordinators
    public int getCoCount() throws SQLException{
        pst1 = Db.connection().prepareStatement("select cordinatorId from cordinators");
        rst1 = pst1.executeQuery();
        count=0;
        
        int[] id=new int[1000];
        
        while (rst1.next()) {
            id[count]=rst1.getInt("cordinatorId");
            count++;
            
        }
        return  count;
    }
    
     public void updateUser(String password,String username){
        try {
            pst1 = Db.connection().prepareStatement("update users set password =? WHERE (username = ?)");
            
            pst1.setString(1,password);
            pst1.setString(2,username);
            
            
            pst1.executeUpdate();
            
            System.out.println("UPDATE COMPLETE");
        } catch (SQLException ex) {
            Logger.getLogger(ManageStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
                
     }

    @Override
    public boolean addUser(int userId, String username, String password, String usertype, String fname, String lname) {
         //To change body of generated methods, choose Tools | Templates.
         boolean is =false;
          try {
            
            pst = Db.connection().prepareStatement("select username from users where username = ?");
            pst.setString(1, username);
            rst = pst.executeQuery();
            //System.out.println("test"+rst.getString("username"));
            if(rst.next()){
                is=false;
            }else{
                
                
                String userInfo = "insert into users values(?, ?, ?, ?, ?, ?)";
                pst1 = Db.connection().prepareStatement(userInfo);
                pst1.setInt(1, userId);
                pst1.setString(2, username);
                pst1.setString(3, password);
                pst1.setString(4, usertype);
                pst1.setString(5, fname);
                pst1.setString(6, lname);
                
                
                pst1.executeUpdate();
                System.out.println("INSERT COMPLETE");
                is= true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ManageLecturers.class.getName()).log(Level.SEVERE, null, ex);
        }
          return is;
    }

    @Override
    public void updateUser(String fname, String lname, String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
