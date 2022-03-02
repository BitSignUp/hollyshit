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



public class DeleteServlet extends HttpServlet{

   @Override

   protected void service(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      int n=0;

      request.setCharacterEncoding("UTF-8");

      String id=request.getParameter("id");

      response.setContentType("text/html;charset=UTF-8");

      PrintWriter pw = response.getWriter();


      // DB 연결 및 쿼리 실행
      SqlLink link = new SqlLink();
      n = link.deleteMember(id);
      link.linkDisconnect();


      if(n>0){

         response.sendRedirect("list.do");

      }else{

         pw.println("<html>");

         pw.println("<head></head>");

         pw.println("<body>");

         pw.println("회원삭제에 실패했습니다. ");

         pw.println("<a href='javascript:history.go(-1)'>이전페이지로 가기</a>");

         pw.println("</body>");

         pw.println("</html>");

         pw.close();

      }

   }

}
