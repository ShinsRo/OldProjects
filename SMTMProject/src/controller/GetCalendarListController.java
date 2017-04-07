package controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import model.AccountDAO;
import model.CalendarBean;
import model.CalendarManager;
import model.DayVO;

public class GetCalendarListController implements Controller {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      response.setCharacterEncoding("utf-8");
      PrintWriter out = response.getWriter();
      int year = Integer.parseInt(request.getParameter("year"));
      int month = Integer.parseInt(request.getParameter("month"));
      String monthStr = "";
      if (month < 10)
         monthStr = "0" + month;
      else
         monthStr = "" + month;
      String key = "key"+year+monthStr;

      CalendarManager cm = CalendarManager.getInstance();
      cm.setCurrent(year, month);
      CalendarBean cb = cm.getCurrent();
      
      HashMap<String, DayVO> map = new HashMap<String, DayVO>();
      map = AccountDAO.getInstance().getAllDayList("java");
      ArrayList<DayVO> list = new ArrayList<>(32);
      
      for(int i = 0 ; i<cb.getLastDayOfMonth()+1; i ++){
            if(i>=10) list.add(map.get(key+i));
            else list.add(map.get(key+"0"+i));
      }
      cb.setListOnMonth(list);

      JSONObject cbObj = new JSONObject(cb);
      System.out.println(cbObj.toString());
      out.print(cbObj.toString());
      return null;
   }
}