package controller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.SessionExpiredException;

public class GetCurrentController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		if(session==null||session.getAttribute("mvo")==null){
			throw new SessionExpiredException();
		}
		String go = request.getParameter("go");
		Calendar gCal = GregorianCalendar.getInstance();
		
		String year = "" + gCal.get(Calendar.YEAR);
		String month = "" + (gCal.get(Calendar.MONTH)+1);
		
		month = (gCal.get(Calendar.MONTH)<10) ? 0+month : month;
		
		System.out.println(" "+ year + "/"+ month);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		return go + ".jsp";
	}
	/*public static void main(String[] args) {
		Calendar gCal = GregorianCalendar.getInstance();
		String year = "" + gCal.get(Calendar.YEAR);
		String month = "" + (gCal.get(Calendar.MONTH)+1);
		
		month = (gCal.get(Calendar.MONTH)<10) ? 0+month : month;
		System.out.println(" "+ year + "/"+ month);
	}*/
}
