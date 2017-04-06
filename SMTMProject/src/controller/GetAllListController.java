package controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import model.AccountDAO;
import model.DayVO;

public class GetAllListController implements Controller {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      //HttpSession session = request.getSession();
      PrintWriter pw=response.getWriter();
      //MemberVO vo= (MemberVO)session.getAttribute("mvo");
      
      String id= "java";
      
      HashMap<String,DayVO> map=new HashMap<String,DayVO>();
      map=AccountDAO.getInstance().getAllDayList(id);
      ArrayList<DayVO> list = new ArrayList<>();
      
      TreeSet<String> keyArr = new TreeSet<>(map.keySet());
      for (Iterator<String> iterator = keyArr.iterator(); iterator.hasNext();) {
         list.add(map.get(iterator.next()));
   }
      
      JSONArray arr = new JSONArray(list);
      /*JSONObject js=new JSONObject(map);
      System.out.println(js.toString());
      pw.print(js.toString());*/
      pw.print(arr);
      pw.close();
      return null;
      //return "account/list.jsp";
   }
}