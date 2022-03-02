package Pack;

import java.sql.*;
import java.util.*;

public class SqlLink  {
   static Connection conn = null;
   static Statement stat = null;
   PreparedStatement pstmt = null;
//   String url  = "jdbc:mysql://localhost:3306/world";
   String url  = "jdbc:mysql://15.164.217.65:3306/world";
   String dbId = "cheetah";
   String dbPw = "1234";

   SqlLink() {
      try {
         
         //���� Ŭ���� �ε�
         System.out.println("DB ���� �õ�");
         
         Class.forName("com.mysql.cj.jdbc.Driver"); //DB���� ��ü ����
         conn = DriverManager.getConnection(url, dbId, dbPw);
         stat = conn.createStatement();
         
         System.out.println("DB ���� �Ϸ�");
         
      } catch (ClassNotFoundException e) {
         System.out.println(e.getMessage());
      } catch (Exception e) {
         System.out.println("DB ���� ����"); 
         e.printStackTrace(System.out); 
      }

   }
   
   /**
    * DB ������ ���´�.
    */
   void linkDisconnect(){
         try {
            
            if(pstmt!=null) pstmt.close();
            if(conn!=null) conn.close();
            
         } catch (SQLException e) { e.printStackTrace(); }
      
   }
   
   Boolean isConnected() {
      return (conn != null) ? true : false; 
   }

   /**
    * ȸ������
    * - members ���̺� �� row�� �߰��Ѵ�.
    */
   int signUp(String id, String pw, String name, String nick, String mail, String phone) {
      
      if(!isConnected()) {
         System.out.println("DB ����ȵ�");
         return 0;
      }
      
      // �Է� ������ �˻��ϱ�
      //
      //
      //

      String sql = "insert into world.members(id, pw, name, nick, mail, phone) values( ?,?,?,?,?,?)"; 
      int cnt = 0;
      try {
         pstmt = conn.prepareStatement(sql);

         pstmt.setString(1, id); 
         pstmt.setString(2, pw); 
         pstmt.setString(3, name); 
         pstmt.setString(4, nick);
         pstmt.setString(5, mail); 
         pstmt.setString(6, phone); 

         cnt = pstmt.executeUpdate();
         
      } catch (SQLIntegrityConstraintViolationException e) {
         System.out.println("���̵� �ߺ��Ǵ� row�� ����");
         e.printStackTrace(); return -1;
      } catch (SQLException e) {
         e.printStackTrace(); 
         return -2;
      }

      return cnt;

   }

   /**
    * �Է��� ���̵�� ��й�ȣ�� ��ġ�ϴ��� Ȯ���Ѵ�.
    * @param   id
    * @param    pw
    * @return   �Է��� ���̵�� ��й�ȣ�� ��ġ�ϴ� �̸��� ��ȯ�Ѵ�. ���ٸ� null�� ��ȯ�ȴ�.
    */
   String checkLogin(String id, String pw) {
      
      String sql = "select name from world.members where id='"+ id +"' and pw='" + pw + "'";
      String name = null;
      try {
         pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery();

         while(rs.next()){

            name = rs.getString("name");
            System.out.println(name);
         }
         
      } catch (SQLException e) { e.printStackTrace(); }
      
//      rs.close();

      return name;
      
   }
   

   /**
    * �� �̸� ��������
    * - members ���̺��� ���� �α����� ���̵��� �̸��� �ҷ��´�.
    */
   String getMyName(String login_id) {
      
      login_id = "sksaaa00";
      String sql = "select name from world.members where id='"+ login_id +"'";
      String name = null;
      try {
         pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery();

         while(rs.next()){

            name = rs.getString("name");
            System.out.println(name);
         }
      } catch (SQLException e) { e.printStackTrace(); }

//      rs.close();
      
      return name;
   }

   /**
    * ȸ������ ��� ���� �ҷ�����
    * @return members ��ü row
    */
   ResultSet getMembers() {
      System.out.println("getMembers ����");
      
      String sql = "select * from members";
      ResultSet rs = null;
      try {
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
      } catch (SQLException e) {
         e.printStackTrace();
      }

//      rs.close();
      
      return rs;
      
   }
   
   
   ResultSet getMember(String id) {

      String sql = "select * from members where id=?";
      ResultSet rs = null;
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, id);
         rs = pstmt.executeQuery();
      } catch (SQLException e) { e.printStackTrace(); }

      return rs;
   }
   
   /**
    * Member�� row �׸��� �����Ѵ�.
    * @param    pwd
    * @param    email
    * @param    phone
    * @return   ������ ����
    */
   int setMember(String pwd, String email, String phone, String id) {
      System.out.println("setMember ����");

      String sql = "update members set pw=?, mail=?, phone=? where id=?";
      int cnt = 0;
      try {
         pstmt = conn.prepareStatement(sql);
         
         pstmt.setString(1, pwd);
         pstmt.setString(2, email);
         pstmt.setString(3, phone);
         pstmt.setString(4, id);

         cnt = pstmt.executeUpdate();
      } catch (SQLException e) { e.printStackTrace(); }

      return cnt;
   }
   
   /**
    * ��� ����
    * @param   id
    * @return   ������ ����. ���ٸ� 0.
    */
   int deleteMember(String id) {

      String sql = "delete from members where id=?";
      int cnt = 0;
      try {

         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, id);
         cnt = pstmt.executeUpdate();

      } catch (SQLException e) {
         e.printStackTrace();
      }

      return cnt;

   }
   
   
   /**
    * ���� �ҷ�����
    * - guest ���̺��� ��� ������ �ҷ��´�.
    */
   ResultSet getGuest() {

      String sql = "select * from guest";
      ResultSet rs = null;
      try {
         pstmt = conn.prepareStatement(sql);

         rs = pstmt.executeQuery();

         return rs;
//         while(rs.next()){
//
//            String name = rs.getString("name");
//            String upload_date = rs.getString("upload_date"); 
//            String contents = rs.getString("contents");
//            String id2 = rs.getString("id"); 
//            String guest_id = rs.getString("guest_id");
//
//            System.out.println(name + upload_date+ contents+id2 + guest_id); 
//         }

      } catch (SQLException e) { e.printStackTrace(); }
      
      return rs;
   }

   /**
    * ���� ���
    * @param id
    * @param name
    * @param upload_date
    * @param contents
    * @return
    */
   int setGuest(String id, String upload_date, String contents) {
      System.out.println("setGuest");
      String sql = "insert into guest(id, upload_date, contents) values( ?, sysdate(),?)"; 
      int cnt = 0;
      try {
         pstmt = conn.prepareStatement(sql);

         pstmt.setString(1, id);  
         pstmt.setString(2, contents); 

         cnt = pstmt.executeUpdate();
         
      } catch (SQLException e) {
         e.printStackTrace(); 
         return -2;
      }

      return cnt;
      
   }
   
   

   

   
}


