/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hansani Perera
 */
public class ManageStudent extends ManageUser{
    private  PreparedStatement pst;
    private  ResultSet rst;
    private  PreparedStatement pst1;
    private  ResultSet rst1;
    int sid[] = new int[1000];
    int bid[] = new int[1000];
    int cid[] = new int[1000];
    int count = 0,c=0;
    
    //add new student data
       
     public void addUser(int number, String mname,String dob,String address,String telephone,File im,int i,int batch,String date ) throws SQLException, FileNotFoundException, ParseException{              
        
        
         
        String userInfo = "insert into students values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pst = Db.connection().prepareStatement(userInfo);
        pst.setInt(1,number);
        pst.setString(2,mname);
        pst.setString(3,dob );
        pst.setString(4, address);
        pst.setString(5,telephone);
        FileInputStream fin = new FileInputStream(im);
        pst.setBinaryStream(6, fin,(int) im.length());
        pst.setInt(7, i);
        pst.setString(8,date);
        pst.setInt(9,batch);
              
                       
        pst.executeUpdate();          
        System.out.println("INSERT COMPLETE");
    }
     
     //get student count
     public int getStudentCount() throws SQLException{
        pst = Db.connection().prepareStatement("select studentId from students");
        rst = pst.executeQuery();
        count=0;
        
        int[] id=new int[1000];
        
        while (rst.next()) {
            id[count]=rst.getInt("studentId");
            count++;
            
        }
        return  count;
    }
     
    @Override
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
     
     //update user
     public void updateUser(String fname,String lname,String username){
        try {
            pst1 = Db.connection().prepareStatement("update users set fname=?,lname=? WHERE (username = ?)");
            pst1.setString(1,fname);
            pst1.setString(2,lname);
            pst1.setString(3, username);
            pst1.executeUpdate();
            
            System.out.println("UPDATE COMPLETE");
        } catch (SQLException ex) {
            Logger.getLogger(ManageStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     //update student information
    public void updateUser(String mname,String dob,String address,String telephone,int batch,String date,String username) throws SQLException{
                
        int id = 0;
                    
        pst1=Db.connection().prepareStatement("select userId from users WHERE (username = ?)");
        pst1.setString(1,username);
        rst1 = pst1.executeQuery();
        while (rst1.next()) {
          id=rst1.getInt("userId");
        }
        System.out.println(+id);
        pst = Db.connection().prepareStatement("update students set mname=?,dob=?,address=?,telephone=?,batch=?,"
                + "registerDate=? WHERE (userId= ?)"); 
    
        pst.setString(1,mname);
        pst.setString(2,dob);
        pst.setString(3,address);
        pst.setString(4,telephone);
        pst.setInt(5,batch);
        pst.setString(6,date);
        pst.setInt(7,id);
                       
        pst.executeUpdate(); 
                       
           
        System.out.println("UPDATE COMPLETE");
    }
    
    //get student id to an array
    public int[] loadStudentId() throws SQLException{
        c=0;
        pst = Db.connection().prepareStatement("select distinct studentId from students");
        rst = pst.executeQuery();
        
        
        while (rst.next()) {
            sid[c]=rst.getInt("studentId");
            c++;
        }
        return  sid;
    }
    //get course id to an array
    public int[] loadCourse() throws SQLException{
        c=0;
        pst = Db.connection().prepareStatement("select distinct courseId from courses");
        rst = pst.executeQuery();
        
        
        while (rst.next()) {
            cid[c]=rst.getInt("courseId");
            c++;
        }
        return  cid;
    }
    
    //get batch nuber to an array
    public int[] loadbatch() throws SQLException{
        c=0;
        pst = Db.connection().prepareStatement("select distinct batch from students");
        rst = pst.executeQuery();
        
        
        while (rst.next()) {
            bid[c]=rst.getInt("batch");
            c++;
        }
        return  bid;
    }
    
    //show student details
     public void setStudentData(DefaultTableModel model) throws SQLException{
        pst = Db.connection().prepareStatement("select distinct students.studentId,students.mname,students.dob,"+
                "students.address,students.telephone,students.registerDate,students.batch,users.fname,users.lname "
                + "from "+
              "students,users where students.userId=users.userId");
        rst = pst.executeQuery();
        
        while(rst.next()){
            
                int studentId = rst.getInt("studentId");
                String name = rst.getString("fname")+" "+rst.getString("mname")+" "+ rst.getString("lname");
                String dob = rst.getString("dob");
                String address = rst.getString("address");
                String telephone = rst.getString("telephone");
                String regDate = rst.getString("registerDate");
                int batch =rst.getInt("batch");
                Object[] row = {studentId,name,dob,address,telephone,regDate,batch};
                
                model.addRow(row);
            

        }
        
    }
     
     //get selected student details
     public void setSelectedStudentDetails(DefaultTableModel model,int id) throws SQLException{
        pst = Db.connection().prepareStatement("select distinct students.studentId,students.mname,students.dob,"+
                "students.address,students.telephone,students.registerDate,students.batch,users.fname,users.lname "
                + "from "+
              "students,users where students.userId=users.userId"+
             " and students.studentId =?");
        pst.setInt(1, id);
        rst = pst.executeQuery();

        
        while(rst.next()){
            int studentId = rst.getInt("studentId");
            String name = rst.getString("fname")+" "+rst.getString("mname")+" "+ rst.getString("lname");
            String dob = rst.getString("dob");
            String address = rst.getString("address");
            String telephone = rst.getString("telephone");
            String regDate = rst.getString("registerDate");
            int batch =rst.getInt("batch");
            Object[] row = {studentId,name,dob,address,telephone,regDate,batch};
            
            model.addRow(row);

        }
     }
     
     public void setSelectedBatchDetails(DefaultTableModel model,int batch) throws SQLException{
        pst = Db.connection().prepareStatement("select distinct students.studentId,students.mname,students.dob,"+
                "students.address,students.telephone,students.registerDate,students.batch,users.fname,users.lname "
                + "from "+
              "students,users where students.userId=users.userId"+
             " and students.batch =?");
        pst.setInt(1, batch);
        rst = pst.executeQuery();

        
        while(rst.next()){
            int studentId = rst.getInt("studentId");
            String name = rst.getString("fname")+" "+rst.getString("mname")+" "+ rst.getString("lname");
            String dob = rst.getString("dob");
            String address = rst.getString("address");
            String telephone = rst.getString("telephone");
            String regDate = rst.getString("registerDate");
            int b =rst.getInt("batch");
            Object[] row = {studentId,name,dob,address,telephone,regDate,b};
            
            model.addRow(row);

        }
     }
     
     
     //get student information from studentId from student table
    public String[] getInfo(int studentId) throws SQLException{
        String[] row = new String[6];
        pst = Db.connection().prepareStatement("select mname,dob,address,telephone,registerDate,batch from students where "
                + "studentId = ?");
        pst.setInt(1, studentId);
        rst = pst.executeQuery();
        
        while (rst.next()) {            
            
            row[0] = rst.getString("mname");
            row[1] = rst.getString("dob");
            row[2] = rst.getString("address");
            row[3] = rst.getString("telephone");
            row[4] = rst.getString("registerDate");
            row[5] = rst.getString("batch");
            
        }
        
        return row;
    }
     
      // get student info from username
      public String[] getInfo(String name) throws SQLException{
        String[] row = new String[7];
        pst = Db.connection().prepareStatement("select mname,dob,address,telephone,registerDate,batch from students,users "
                + "where users.userId = students.userId\n" +
               "and  username = ?");
        pst.setString(1, name);
        rst = pst.executeQuery();
        
        while (rst.next()) {            
            
            row[0] = rst.getString("mname");
            row[1] = rst.getString("dob");
            row[2] = rst.getString("address");
            row[3] = rst.getString("telephone");
            row[4] = rst.getString("registerDate");
            row[5] = rst.getString("batch");
            
        }
        
        return row;
    }
    
    public BufferedImage getImage(String name) throws SQLException{
        BufferedImage im = null ;
        pst=Db.connection().prepareStatement("select image from students,users where users.userId = students.userId\n" +
               "and  username = ?"); 
        pst.setString(1, name);
        rst = pst.executeQuery();
        
        if(rst.next()){
            try {
                im = ImageIO.read(rst.getBinaryStream("image"));
                //  BackGroundImage.setIcon(new ImageIcon(im));   
            } catch (IOException ex) {
                Logger.getLogger(ManageStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return im;
    }  

    @Override
    public boolean addUser(int userId, String username, String password, String usertype, String fname, String lname) {
        boolean is =false;
        try {
            //To change body of generated methods, choose Tools | Templates.
            
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
            Logger.getLogger(ManageStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return is;
    }
    
    //get user information
     public String[] getUserInfo(int userId) throws SQLException{
        String[] row = new String[4];
        pst = Db.connection().prepareStatement("select fname,lname,username,password from users,students where "+
                "users.userId = students.userId and\n" +
               " students.studentId = ?");
        pst.setInt(1, userId);
        rst = pst.executeQuery();
        
        while (rst.next()) {            
            
            row[0] = rst.getString("fname");
            row[1] = rst.getString("lname");
            row[2] = rst.getString("username");
            row[3] = rst.getString("password");
        }
        
        return row;
    }
     
     public String[] getUserInfo(String name) throws SQLException{
        String[] row = new String[4];
        pst = Db.connection().prepareStatement("select username,password,fname,lname from users where "+
            " username = ?");
        pst.setString(1, name);
        rst = pst.executeQuery();
        
        while (rst.next()) {            
            
            row[0] = rst.getString("username");
            row[1] = rst.getString("password");
            row[2] = rst.getString("fname");
            row[3] = rst.getString("lname");
        }
        
        return row;
    }

    
}