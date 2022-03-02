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
         
         //동적 클래스 로드
         System.out.println("DB 접속 시도");
         
         Class.forName("com.mysql.cj.jdbc.Driver"); //DB연결 객체 생성
         conn = DriverManager.getConnection(url, dbId, dbPw);
         stat = conn.createStatement();
         
         System.out.println("DB 접속 완료");
         
      } catch (ClassNotFoundException e) {
         System.out.println(e.getMessage());
      } catch (Exception e) {
         System.out.println("DB 접속 실패"); 
         e.printStackTrace(System.out); 
      }

   }
   
   /**
    * DB 연결을 끊는다.
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
    * 회원가입
    * - members 테이블에 새 row를 추가한다.
    */
   int signUp(String id, String pw, String name, String nick, String mail, String phone) {
      
      if(!isConnected()) {
         System.out.println("DB 연결안됨");
         return 0;
      }
      
      // 입력 데이터 검사하기
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
         System.out.println("아이디가 중복되는 row가 존재");
         e.printStackTrace(); return -1;
      } catch (SQLException e) {
         e.printStackTrace(); 
         return -2;
      }

      return cnt;

   }

   /**
    * 입력한 아이디와 비밀번호가 일치하는지 확인한다.
    * @param   id
    * @param    pw
    * @return   입력한 아이디와 비밀번호가 일치하는 이름을 반환한다. 없다면 null이 반환된다.
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
    * 내 이름 가져오기
    * - members 테이블에서 현재 로그인한 아이디의 이름을 불러온다.
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
    * 회원가입 목록 전부 불러오기
    * @return members 전체 row
    */
   ResultSet getMembers() {
      System.out.println("getMembers 실행");
      
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
    * Member의 row 항목을 수정한다.
    * @param    pwd
    * @param    email
    * @param    phone
    * @return   수정된 개수
    */
   int setMember(String pwd, String email, String phone, String id) {
      System.out.println("setMember 시작");

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
    * 멤버 삭제
    * @param   id
    * @return   삭제한 개수. 없다면 0.
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
    * 방명록 불러오기
    * - guest 테이블의 모든 내용을 불러온다.
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
    * 방명록 등록
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


