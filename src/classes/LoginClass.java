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
public class LoginClass {
    private  PreparedStatement pst1;
    private PreparedStatement pst;
    private ResultSet rs1;
    private ResultSet rst;
    private String userName,password;
    private int loginStatus = 0; ////0-admin, 1-lecturer, 2-coordinator , 3-student
    
    
    //get login credentials
     public boolean login(String userName,String password) throws SQLException{
        this.userName = userName;
        this.password = password;
        pst1=Db.connection().prepareStatement("select * from users where username=? and password=?");
        pst1.setString(1, userName);
        pst1.setString(2, password);
        rs1=pst1.executeQuery();
        
        pst = Db.connection().prepareStatement("select usertype from users where  username=? and password=?");
         pst.setString(1, userName);
        pst.setString(2, password);
        rst = pst.executeQuery();
        
        
        
        if(rst.next()){
            String type = rst.getString("usertype");
            System.out.println(type);
            if(type.equals("student")){
                loginStatus = 3;
          
            }else if(type.equals("lecturer")){
                loginStatus = 1;
               
            }else if(type.equals("coordinator")){
                loginStatus = 2;
                 
            }else if(type.equals("admin")){
                loginStatus = 0;
                 
            }
           return true;
            }else{ 
            return false;
            }
     }
     
     //get username
     public String getUsername(){
        return userName;
    }
    
     //get password
    public String getpwd(){
        return password;
    }
    
    //get loginstatus
    public int getLoginStatus(){
        return loginStatus;
    }
}
