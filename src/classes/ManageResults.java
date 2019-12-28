/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hansani Perera
 */
public class ManageResults {
    
    private PreparedStatement pst1;
    private  ResultSet rst1;
    private int count = 0;
    
     //add new result   
    public void addRes(int id,String result,int studentId,int course,int batch) throws SQLException{              
        String userInfo = "insert into results values(?, ?, ?, ?, ?)";
        pst1 = Db.connection().prepareStatement(userInfo);
        pst1.setInt(1,id) ;
        pst1.setString(2, result);
        pst1.setInt(3, studentId);
        pst1.setInt(4,course);
        pst1.setInt(5,batch);
        
        
                       
        pst1.executeUpdate();          
        System.out.println("INSERT COMPLETE");
    }
    
    //get total result count
     public int getResultCount() throws SQLException{
        pst1 = Db.connection().prepareStatement("select resultId from results");
        rst1 = pst1.executeQuery();
        count=0;
        
        int[] id=new int[1000];
        
        while (rst1.next()) {
            id[count]=rst1.getInt("resultId");
            count++;
            
        }
        return  count;
    }
     
     //update results
     public void updateResult(String result,int id,int batch,int course) throws SQLException{
        pst1 = Db.connection().prepareStatement("update results set result =? WHERE ( studentId= ? and batchID =? and courseId = ?)"); 
    
        pst1.setString(1,result);
        pst1.setInt(2,id);
        pst1.setInt(3, batch);
        pst1.setInt(4, course);
       
                       
        pst1.executeUpdate();          
           
        System.out.println("UPDATE COMPLETE");
    }
     
    //set student results     
     public void setSelectedStudentResult(DefaultTableModel model,String user) throws SQLException{
        pst1 = Db.connection().prepareStatement("select courses.name,results.result, students_has_courses.courseId  from users,\n" +
         "students,students_has_courses,courses ,results\n" +
         "where students.userId=users.userId and students.studentId=students_has_courses.studentId\n" +
         "and students_has_courses.courseId = courses.courseId and results.studentId = students.studentId\n" +
         "and results.batchId = students.batch and results.courseId = courses.courseId\n" +
         "and users.username = ?");
        pst1.setString(1, user);
        rst1 = pst1.executeQuery();

        
        while(rst1.next()){
            int course= rst1.getInt("courseId");
            String name = rst1.getString("name");
            String result = rst1.getString("result");
            
            Object[] row = {course,name,result};
            
            model.addRow(row);

        }
     }
     
      //set student results     
     public void setSelectedStudentResult(DefaultTableModel model,int id) throws SQLException{
        pst1 = Db.connection().prepareStatement("select courses.name,results.result,courses.courseId  from \n" +
"		courses ,results\n" +
"         where results.studentId = ?");
        pst1.setInt(1, id);
        rst1 = pst1.executeQuery();

        
        while(rst1.next()){
            int course= rst1.getInt("courseId");
            String name = rst1.getString("name");
            String result = rst1.getString("result");
            
            Object[] row = {course,name,result};
            
            model.addRow(row);

        }
     }
     
     public void setSelectedBatchtResult(DefaultTableModel model,int batchId) throws SQLException{
        pst1 = Db.connection().prepareStatement("select results.studentId, courses.name,results.result,courses.courseId from\n" +
               "courses , results where courses.batchId = ?");
        pst1.setInt(1, batchId);
        rst1 = pst1.executeQuery();

        
        while(rst1.next()){
            int student= rst1.getInt("studentId");
            String id = rst1.getString("courseId");
            String name = rst1.getString("name");
            String result = rst1.getString("result");
            
            Object[] row = {student,id,name,result};
            
            model.addRow(row);

        }
     }

}
     
     

