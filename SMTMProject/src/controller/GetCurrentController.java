package controller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCurrentController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
