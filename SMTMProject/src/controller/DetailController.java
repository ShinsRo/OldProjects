package controller;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import model.AccountDAO;
import model.AccountVO;
import model.MemberVO;

public class DetailController implements Controller {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      response.setContentType("text/html;charset=utf-8");
      ArrayList<AccountVO> list = new ArrayList<AccountVO>();
      String today = request.getParameter("today");
      // String today = "2017/04/06";
      PrintWriter pw = response.getWriter();

      // HttpSession session = request.getSession();
      // MemberVO vo = (MemberVO)session.getAttribute("dao");
      // String id = vo.getId();
      String id = "java";

      list = AccountDAO.getInstance().getDetailList(today, id);

      JSONArray js = new JSONArray(list);
      System.out.println(js.toString());
      pw.println(js.toString());

         pw.close();
         return null;
   }
}