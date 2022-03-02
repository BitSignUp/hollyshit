package Pack;

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TryCatchFinally;

public class Guest extends HttpServlet{

   @Override

   protected void service(HttpServletRequest request, HttpServletResponse response)

         throws ServletException, IOException {

      // 1. �Ķ���ͷ� ���۵� ���� ������.

//      request.setCharacterEncoding("euc-kr");
      request.setCharacterEncoding("UTF-8");

      String id      = request.getParameter("id");
      String pwd      = request.getParameter("pw");
      
      // 2. DB�� ����
      SqlLink link = new SqlLink();
      // ���� �ҷ�����
      
      String name = link.checkLogin(id, pwd);
      ResultSet rs = link.getGuest();
//      link.linkDisconnect();

      // 3. �����(Ŭ���̾�Ʈ)�� ����� �����ϱ�.
//      response.setContentType("text/html;charset=euc-kr");
      response.setContentType("text/html; charset=UTF-8");


      PrintWriter pw = response.getWriter();

   

      if(name != null){

         pw.println(getPage(id, pwd, rs));

      }
      else{
         pw.println("<html>");

         pw.println("<head></head>");

         pw.println("<body>");
         pw.println("��й�ȣ�� Ʋ�Ȱų� ���̵� �������� �ʽ��ϴ�.<br/>");
         pw.println("<a href='javascript:history.go(-1)'>������������ ����</a>");
         pw.println("</body>");

         pw.println("</html>");
      }
   
   }
   
   String getPage(String id, String pwd, ResultSet rs) {
      
      // ���� ��� �����
      String trList = "";
      try {
         while(rs.next()){

            String id2    = rs.getString("id");
            String update_date   = rs.getString("upload_date");
            String contents = rs.getString("contents");
            
            System.out.println(id2);

            trList += "<tr>"
            + "   <td>" + update_date
            + " </td> "
            + "    <td>" + id2 + "</td>"
            + "    <td> : " + contents + "</td>"
            + "</tr>";

         }
      } catch (SQLException e) { e.printStackTrace(); }
      finally {
         System.out.println("=======");
         System.out.println(trList);
      }

      
      
      return ""
            + "<html>"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"mycss.css\">"
            + "<head>"
            + "</head>"
            + "<body>"
            + "<div>"
            + "<div style='display: block;'>"
            + id + " �� �α��� ���Դϴ�."
            + "</div>"
            + "<div>"
            
            + "</div>"
            + "<form method='get' action='board.do'>"
            + "<label for='story'>Tell us your story</label>"
            + "<div style='overflow: auto; height: 400px; width: 500px;'>"
            
            + "<table>"
            + trList
            + "</table>"
            
//            + "<textarea id='story' name='story' placeholder='���� ���̴°�'>"
//            + "</textarea>"
            + "</div>"
            
            + "<div id='guests' style='display: block;'>"
            + "</div>"
            
            + "<div>"
            + "<input type='text' name='contents' placeholder='������ �Է��ϼ���'></input>"
            + " <input type=\"hidden\" name=\"id\" value='"+ id +"'/>"
            + " <input type=\"hidden\" name=\"pw\" value='"+ pwd +"'/>"
            + "<input type=\"submit\" style=\"margin-right: 10px;\" value=\"�ø���\">"
//            + "<a href='board.do?id=" + id + "&pw=" + pwd + "'> �ø���</a>"
            + "</div>"
            + "</div>"
            + "</form>"
            
//            + "<script>"
//            + getFucTest()
//            + "</script>"
            
            + "</body>"
            + "</html>";
            
   }
   
   String getFucTest() {
      return
      "document.getElementById('upload').onclick = function(){"
      + "let newTag = document.createElement('label');"
      + "newTag.innerHTML = '12345';"
      + "document.getElementById('guests').appendChild(newTag)"
      + "}";
   }

}