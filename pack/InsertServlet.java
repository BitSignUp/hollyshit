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

      // 1. 파라미터로 전송된 값을 얻어오기.

//      request.setCharacterEncoding("euc-kr");
      request.setCharacterEncoding("UTF-8");

      String id      = request.getParameter("id");
      String pwd      = request.getParameter("pw");
      String name      = request.getParameter("name");
      String nick    = request.getParameter("nick");
      String email    = request.getParameter("email");
      String phone    = request.getParameter("phone");
      
      // 2. DB에 전달
      SqlLink link = new SqlLink();
      int n = link.signUp(id, pwd, name, nick, email, phone);
      link.linkDisconnect();

      // 3. 사용자(클라이언트)에 결과를 응답하기.
//      response.setContentType("text/html;charset=euc-kr");
      response.setContentType("text/html; charset=UTF-8");

      PrintWriter pw = response.getWriter();

      pw.println("<html>");

      pw.println("<head></head>");

      pw.println("<body>");

      if(n>0){

         pw.println( id + "님! 성공적으로 가입되었습니다.<br/>");

      }
      else if(n == -1) {
         pw.println("중복되는 아이디가 존재합니다.<br/>");
         pw.println("<a href='javascript:history.go(-1)'>이전페이지로 가기</a>");
      }
      else{

         pw.println("오류로 인해 가입에 실패했습니다.<br/>");
         pw.println("<a href='javascript:history.go(-1)'>이전페이지로 가기</a>");

      }

      pw.println("</body>");

      pw.println("</html>");

   }

}