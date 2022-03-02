package Pack;

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Timestamp;



import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;



public class ListServlet extends HttpServlet{

   @Override

   protected void service(HttpServletRequest resquest, HttpServletResponse response)

         throws ServletException, IOException {

      ResultSet rs = null;

//      response.setContentType("text/html;charset=euc-kr");
      response.setContentType("text/html;charset=UTF-8");

      
      PrintWriter pw = response.getWriter();

      pw.println("<html>");
      pw.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"mycss.css\">");
      pw.println("<head></head>");

      pw.println("<body>");
      
      SqlLink link = null;
      try{

         link = new SqlLink();
         rs = link.getMembers();

         pw.println("<div>");
         pw.println("<table border='1' width='1200'>");
         pw.println("   <tr>");
         pw.println("      <td>���̵�</td>");
         pw.println("      <td>��й�ȣ</td>");
         pw.println("      <td>�̸���</td>");
         pw.println("      <td>��ȭ��ȣ</td>");
//         pw.println("      <td>�����</td>");
         pw.println("      <td>����</td>");
         pw.println("      <td>����</td>");
         pw.println("   </tr>");

         while(rs.next()){

            String id    = rs.getString("id");
            String pwd   = rs.getString("pw");
            String email = rs.getString("mail");
            String phone = rs.getString("phone");

//            Timestamp regdate = rs.getTimestamp("regdate");

            pw.println("<tr>");
            pw.println("   <td>" + id + "</td>");
            pw.println("    <td>" + pwd + "</td>");
            pw.println("    <td>" + email + "</td>");
            pw.println("    <td>" + phone + "</td>");
//            pw.println("    <td>" + regdate + "</td>");
            pw.println("    <td><a href='delete.do?id=" + id + "'>����</a></td>");
            pw.println("    <td><a href='update.do?id=" + id + "'>����</a></td>");
            pw.println("</tr>");

         }

         pw.println("</table>");
         pw.println("</div>");

         pw.println("<a href='main.html'>������������ �̵�</a>");

      } catch(SQLException se){ System.out.println(se.getMessage());
      } finally{

         try{

            if(rs!=null) rs.close();
            link.linkDisconnect();
            
         }catch(SQLException se){  System.out.println(se.getMessage());  }

      }

      pw.println("</body>");
      pw.println("</html>");

   }

}