package Pack;

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;



import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;



public class UpdateServlet extends HttpServlet{

   @Override

   protected void doGet(HttpServletRequest req, HttpServletResponse resp)

         throws ServletException, IOException {

      // 1. parameter로 전송된 id얻기.
      String id=req.getParameter("id");


      // 2. id에 해당하는 정보를 db에서 조회해서 출력.
//      resp.setContentType("text/html;charset=euc-kr");
      resp.setContentType("text/html;charset=UTF-8");

      PrintWriter pw = resp.getWriter();

      pw.println("<html>");

      pw.println("<head></head>");

      pw.println("<body>");


      ResultSet rs = null;
      SqlLink link = null;
      
      try{

         // 2. 전송된 값을 db에 저장.
         link = new SqlLink();
         rs = link.getMember(id);
         
         
         rs.next();

         String pwd   = rs.getString("pw");
         String email = rs.getString("mail");
         String phone = rs.getString("phone");

         

         pw.println("<form method='post' action='updateok.do'>");

         pw.println("<input type='hidden' name='id' value='" + id + "'/>");

         pw.println("아이디<input type='text' name='id' value='" + id + "' disabled='disabled'/><br/>");

         pw.println("비밀번호<input type='text' name='pwd' value='" + pwd + "'/><br/>");

         pw.println("email<input type='text' name='email' value='" + email + "'/><br/>");

         pw.println("phone<input type='text' name='phone' value='" + phone + "'/><br/>");

         pw.println("<input type='submit' value='저장'/><br/>");

         pw.println("</form>");

         

      }
      catch(SQLException se){

         System.out.println(se.getMessage());

      }finally{

         try{

            if(rs!=null) rs.close();
            link.linkDisconnect();
            
         }catch(SQLException se){  System.out.println(se.getMessage());  }

      }


      pw.println("</body>");

      pw.println("</html>");

      pw.close();

   }

}