package controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import model.AccountDAO;
import model.CalendarBean;
import model.CalendarManager;

public class GetCalendarListController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		CalendarManager cm = CalendarManager.getInstance();
		cm.setCurrent(year, month);
		CalendarBean cb = cm.getCurrent();
		AccountDAO dao = AccountDAO.getInstance();
		JSONObject cbObj = new JSONObject(cb);
		out.print(cbObj);
		return null;
	}
}
