package controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import exception.SessionExpiredException;
import model.AccountDAO;
import model.CalendarBean;
import model.CalendarManager;
import model.DayVO;
import model.MemberDAO;
import model.MemberVO;

public class GetCalendarListController implements Controller {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   HttpSession session = request.getSession();
	   if(session==null||session.getAttribute("mvo")==null){
		   throw new SessionExpiredException();
	   }
      response.setCharacterEncoding("utf-8");
      PrintWriter out = response.getWriter();
      int year = Integer.parseInt(request.getParameter("year"));
      int month = Integer.parseInt(request.getParameter("month"));
      String monthStr = "";
      
      
      
      MemberVO mvo = (MemberVO) session.getAttribute("mvo");
      
      
      int currDay = GregorianCalendar.getInstance().get(Calendar.DAY_OF_MONTH);
      //--------------------------------------------
      int limit = MemberDAO.getInstance().getInfo(mvo.getId());//100000
      int total = 0;//1일부터 오늘까지의 총지출 변수
      String ryb = null;
      //total이랑 limit비교
      //rvb = r/y/b
      monthStr = (month < 10)? "0" + month: ""+month;
 
      String key = "key"+year+monthStr;
      
      CalendarManager cm = CalendarManager.getInstance();
      cm.setCurrent(year, month);
      CalendarBean cb = cm.getCurrent();
      cb.setRyb(ryb);
      
      HashMap<String, DayVO> map = new HashMap<String, DayVO>();
      map = AccountDAO.getInstance().getAllDayList(mvo.getId());
      ArrayList<DayVO> list = new ArrayList<>(32);
      
      for(int i = 0 ; i<cb.getLastDayOfMonth()+1; i ++){
            if(i>=10) list.add(map.get(key+i));
            else list.add(map.get(key+"0"+i));
      }
     
      for(int i=1;i<=currDay;i++){
    	  if(list.get(i)!=null)
    		total += list.get(i).getTotalSpend();
      }
      //System.out.println(total);
      
      if(total >= (limit*0.7)){
    	  ryb = "red";
      }else if(total >= limit*0.5 && total < limit * 0.7){
    	  ryb = "yellow";
      }else if(total<limit*0.5){
    	  ryb = "green";
      }
      //System.out.println(ryb);
      cb.setListOnMonth(list);
      cb.setRyb(ryb);
      JSONObject cbObj = new JSONObject(cb);
      System.out.println(cbObj.toString());
      out.print(cbObj.toString());
      return null;
   }
}