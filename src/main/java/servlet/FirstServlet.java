
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//서블릿이 되기 위한 몇가지(추후 정리)
//URL 필요
//@WebServlet(urlPatterns={"/first.cc"}, name="/first.cc")
@WebServlet(name = "first.cc")
public class FirstServlet extends HttpServlet{
   
   private static final long serialVersionUID = 1L;
   
   public FirstServlet() {
      super();
   }

   //get 요청을 처리하는 메소드 - 인자 2개 (XXXRequest, XXXResponse .. 요청과 응답에 대한 처리 가능)
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
   }
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      doGet(req, resp);
}