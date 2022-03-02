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

      // 1. 파라미터로 전송된 값을 얻어오기.

//      request.setCharacterEncoding("euc-kr");
      request.setCharacterEncoding("UTF-8");

      String id      = request.getParameter("id");
      String pwd      = request.getParameter("pw");
      
      // 2. DB에 전달
      SqlLink link = new SqlLink();
      // 방명록 불러오기
      
      String name = link.checkLogin(id, pwd);
      ResultSet rs = link.getGuest();
//      link.linkDisconnect();

      // 3. 사용자(클라이언트)에 결과를 응답하기.
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
         pw.println("비밀번호가 틀렸거나 아이디가 존재하지 않습니다.<br/>");
         pw.println("<a href='javascript:history.go(-1)'>이전페이지로 가기</a>");
         pw.println("</body>");

         pw.println("</html>");
      }
   
   }
   
   String getPage(String id, String pwd, ResultSet rs) {
      
      // 방명록 목록 만들기
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
            + id + " 님 로그인 중입니다."
            + "</div>"
            + "<div>"
            
            + "</div>"
            + "<form method='get' action='board.do'>"
            + "<label for='story'>Tell us your story</label>"
            + "<div style='overflow: auto; height: 400px; width: 500px;'>"
            
            + "<table>"
            + trList
            + "</table>"
            
//            + "<textarea id='story' name='story' placeholder='방명록 보이는곳'>"
//            + "</textarea>"
            + "</div>"
            
            + "<div id='guests' style='display: block;'>"
            + "</div>"
            
            + "<div>"
            + "<input type='text' name='contents' placeholder='내용을 입력하세요'></input>"
            + " <input type=\"hidden\" name=\"id\" value='"+ id +"'/>"
            + " <input type=\"hidden\" name=\"pw\" value='"+ pwd +"'/>"
            + "<input type=\"submit\" style=\"margin-right: 10px;\" value=\"올리기\">"
//            + "<a href='board.do?id=" + id + "&pw=" + pwd + "'> 올리기</a>"
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