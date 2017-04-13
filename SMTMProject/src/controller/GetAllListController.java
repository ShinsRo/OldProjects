package controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import exception.SessionExpiredException;
import model.AccountDAO;
import model.DayVO;
import model.MemberVO;

public class GetAllListController implements Controller {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   HttpSession session = request.getSession();
		if(session==null||session.getAttribute("mvo")==null){
			throw new SessionExpiredException();
		}
      PrintWriter pw=response.getWriter();
      MemberVO vo= (MemberVO)session.getAttribute("mvo");
      //Session에 저장된 mvo에서id 받아옴
      String id= vo.getId();
      
      HashMap<String,DayVO> map=new HashMap<String,DayVO>();
      map=AccountDAO.getInstance().getAllDayList(id);
      ArrayList<DayVO> list = new ArrayList<>();
      
      TreeSet<String> keyArr = new TreeSet<>(map.keySet());
      for (Iterator<String> iterator = keyArr.iterator(); iterator.hasNext();) {
         list.add(map.get(iterator.next()));
   }
      
      JSONArray arr = new JSONArray(list);
 
      pw.print(arr);
      pw.close();
      return null;
    
   }
}