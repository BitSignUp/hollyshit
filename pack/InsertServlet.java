package Pack;

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.SQLException;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

public class InsertServlet extends HttpServlet{

   @Override

   protected void service(HttpServletRequest request, HttpServletResponse response)

         throws ServletException, IOException {

      // 1. �Ķ���ͷ� ���۵� ���� ������.

//      request.setCharacterEncoding("euc-kr");
      request.setCharacterEncoding("UTF-8");

      String id      = request.getParameter("id");
      String pwd      = request.getParameter("pw");
      String name      = request.getParameter("name");
      String nick    = request.getParameter("nick");
      String email    = request.getParameter("email");
      String phone    = request.getParameter("phone");
      
      // 2. DB�� ����
      SqlLink link = new SqlLink();
      int n = link.signUp(id, pwd, name, nick, email, phone);
      link.linkDisconnect();

      // 3. �����(Ŭ���̾�Ʈ)�� ����� �����ϱ�.
//      response.setContentType("text/html;charset=euc-kr");
      response.setContentType("text/html; charset=UTF-8");

      PrintWriter pw = response.getWriter();

      pw.println("<html>");

      pw.println("<head></head>");

      pw.println("<body>");

      if(n>0){

         pw.println( id + "��! ���������� ���ԵǾ����ϴ�.<br/>");

      }
      else if(n == -1) {
         pw.println("�ߺ��Ǵ� ���̵� �����մϴ�.<br/>");
         pw.println("<a href='javascript:history.go(-1)'>������������ ����</a>");
      }
      else{

         pw.println("������ ���� ���Կ� �����߽��ϴ�.<br/>");
         pw.println("<a href='javascript:history.go(-1)'>������������ ����</a>");

      }

      pw.println("</body>");

      pw.println("</html>");

   }

}