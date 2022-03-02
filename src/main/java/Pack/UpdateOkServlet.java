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



public class UpdateOkServlet extends HttpServlet{

   @Override

   protected void doPost(HttpServletRequest req, HttpServletResponse resp)

         throws ServletException, IOException {

      
      
      req.setCharacterEncoding("euc-kr");
//      req.setCharacterEncoding("UTF-8");

      String id=req.getParameter("id");
      String pwd=req.getParameter("pwd");
      String email=req.getParameter("email");
      String phone=req.getParameter("phone");

      
      int n=0;

      
      SqlLink link = new SqlLink();
      n = link.setMember(pwd, email, phone, id);
      link.linkDisconnect();

      
      if(n>0){
         System.out.println("수정완료");
         resp.sendRedirect("list.do");

      }else{
         System.out.println("수정실패");
         
         PrintWriter pw = resp.getWriter();

         pw.println("<html><head></head>");

         pw.println("<body>실패</body>");

         pw.println("</heal>");

         pw.close();

      }

   }

}