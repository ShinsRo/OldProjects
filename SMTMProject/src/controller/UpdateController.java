package controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.json.JSONArray;

import model.AccountDAO;
import model.AccountVO;
import model.MemberVO;

public class UpdateController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		AccountVO vo = new AccountVO();
		
		String no = request.getParameter("no");
		String today = request.getParameter("today");
		String morningAfternoon = request.getParameter("morningAfternoon");
		String hh = request.getParameter("hh");
		String mm = request.getParameter("mm");
		String inAndOut = request.getParameter("inAndOut");
		String detail = request.getParameter("detail");
		int money = Integer.parseInt(request.getParameter("money"));

		vo.setNo(no);
		vo.setToday(today);
		vo.setType(inAndOut);
		vo.setDetail(detail);
		
		if(inAndOut.equals("income")){
			vo.setIncome(money);
		}else if(inAndOut.equals("spend")){
			vo.setSpend(money);
		}
		
		
		if(morningAfternoon.equals("am")){
			today = today + hh+":"+mm+":"+"00";
			vo.setTime(hh+":"+mm+":"+"00");
		}else if(morningAfternoon.equals("pm")){
			if(hh.equals("12")){
				hh = ""+(Integer.parseInt(hh)+11);
			}else{
				hh = ""+(Integer.parseInt(hh)+12);
			}
			today = today + hh+":"+mm+":"+"00";
			vo.setTime(hh+":"+mm+":"+"00");
		}
		
		AccountDAO.getInstance().updateDetail(vo);

		request.setAttribute("today", today);	
		
		return  "account/popup_result.jsp";
	}

}
