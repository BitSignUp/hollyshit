package Pack;

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.SQLException;



import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;



public class Board extends HttpServlet{

   @Override

   protected void service(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      int n=0;

      request.setCharacterEncoding("UTF-8");

      String id=request.getParameter("id");
      String pwd=request.getParameter("pw");
      String contents=request.getParameter("contents");

      System.out.println(id +"잘 왔니 " + pwd + contents);

      response.setContentType("text/html;charset=UTF-8");

      PrintWriter pw = response.getWriter();


      // DB 연결 및 쿼리 실행
      SqlLink link = new SqlLink();
      n = link.setGuest(id, "1234", contents);
      link.linkDisconnect();


      if(n>0){

         response.sendRedirect("guest?id=" + id + "&pw="+pwd);

      }else{

         pw.println("<html>");

         pw.println("<head></head>");

         pw.println("<body>");

         pw.println("댓글등록 실패 ");

         pw.println("<a href='javascript:history.go(-1)'>이전페이지로 가기</a>");

         pw.println("</body>");

         pw.println("</html>");

         pw.close();

      }

   }

}
