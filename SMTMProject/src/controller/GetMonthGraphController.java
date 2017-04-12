package controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import model.AccountDAO;
import model.CalendarBean;
import model.CalendarManager;
import model.DayVO;
import model.MemberVO;

public class GetMonthGraphController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		String wi = request.getParameter("wi");
		PrintWriter pw = response.getWriter();
		Calendar gCal = GregorianCalendar.getInstance();

	    CalendarBean cb = CalendarManager.getInstance().getCurrent();
	     ArrayList<DayVO> list = new ArrayList<DayVO>();
	     list = cb.getListOnMonth();
	     ArrayList<DayVO> weekList = new ArrayList<>();
	    // fd 시작요일 정보
	     int fd = cb.getFirstDayOfMonth();
	     int totalIncome = 0;
	     int totalSpend = 0;
	     int weekInfo = 1;
	     
	     for(int i = 1 ; i < 8-fd; i ++){
		    if(cb.getListOnMonth().get(i) != null){
		    	totalIncome+=cb.getListOnMonth().get(i).getTotalIncome();
		    	totalSpend+=cb.getListOnMonth().get(i).getTotalSpend();
		    }
		 }
	     weekInfo++;
	     weekList.add(new DayVO("1주차", totalIncome, totalSpend));
	    	 
	     totalIncome = 0;
	     totalSpend = 0;
	     for(int i = 8-fd; i< 7-fd+8*(weekInfo-1);i++){
	    	 if(cb.getListOnMonth().get(i) != null){
			    	totalIncome+=cb.getListOnMonth().get(i).getTotalIncome();
			    	totalSpend+=cb.getListOnMonth().get(i).getTotalSpend();
			    	}
	     }
	     	weekInfo++;
	     	weekList.add(new DayVO("2주차", totalIncome, totalSpend));
	    	
		    totalIncome = 0;
	    	totalSpend = 0;
	    	 for(int i = 7-fd+8*(weekInfo-2); i < 6-fd+8*(weekInfo-1);i++){
	    		 if(cb.getListOnMonth().get(i) != null){
			    		totalIncome+=cb.getListOnMonth().get(i).getTotalIncome();
			    		totalSpend+=cb.getListOnMonth().get(i).getTotalSpend();
			    		}
			    	}
	    	 weekInfo++;
	    	 weekList.add(new DayVO("3주차", totalIncome, totalSpend));

	    	totalIncome = 0;
	    	totalSpend = 0;
	    	 for(int i = 6-fd+8*(weekInfo-2); i < 5-fd+8*(weekInfo-1);i++){
	    		 if(cb.getListOnMonth().get(i) != null){
			    		totalIncome+=cb.getListOnMonth().get(i).getTotalIncome();
			    		totalSpend+=cb.getListOnMonth().get(i).getTotalSpend();
			    		}
			    	}
	    	 	weekInfo++;
		    	 weekList.add(new DayVO("4주차", totalIncome, totalSpend));

	    	 totalIncome = 0;
	    	 totalSpend = 0;
	    	 int flag = cb.getLastDayOfMonth()+1;
	           if(fd == 6){
	              flag = 4-fd+8*(weekInfo-1);
	           }
	    	 for(int i =5-fd+8*(weekInfo-2); i < 4-fd+8*(weekInfo-1);i++ ){
	    		 if(cb.getListOnMonth().get(i) != null){
			    		totalIncome+=cb.getListOnMonth().get(i).getTotalIncome();
			    		totalSpend+=cb.getListOnMonth().get(i).getTotalSpend();
			    		}
			    	}
	    	 weekInfo++;
		    weekList.add(new DayVO("5주차", totalIncome, totalSpend));

		    if(wi.equals("6")){
	    	 totalIncome = 0;
	    	 totalSpend = 0;
	    	 for(int i = 4-fd+8*(weekInfo-2); i <= cb.getLastDayOfMonth();i++){
	    		 if(cb.getListOnMonth().get(i) != null){
			    		totalIncome+=cb.getListOnMonth().get(i).getTotalIncome();
			    		totalSpend+=cb.getListOnMonth().get(i).getTotalSpend();
			    		}
			    	}
		    weekList.add(new DayVO("6주차", totalIncome, totalSpend));
		    }
	    JSONArray json = new JSONArray(weekList);
		pw.println(json.toString());
		pw.close();
		return null;
	}
}