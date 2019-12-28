/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Hansani Perera
 */
public abstract class  ManageUser {
    private PreparedStatement pst1,pst;
    private  ResultSet rst1,rst;
    private int count=0;
    
     //update user information
    public abstract void updateUser(String password,String username);
    //update user information
    public abstract void updateUser(String fname,String lname,String username);
      
    
     //get user count
     public int getUserCount() throws SQLException{
        pst1 = Db.connection().prepareStatement("select userId from users");
        rst1 = pst1.executeQuery();
        count=0;
        
        int[] id=new int[1000];
        
        while (rst1.next()) {
            id[count]=rst1.getInt("userId");
            count++;
            
        }
        return  count;
    }
    // add new User 
    public abstract boolean addUser(int userId,String username,String password,String usertype,String fname, String lname);
       
    
    
    
}
