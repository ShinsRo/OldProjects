package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccountDAO;
import model.AccountVO;
import model.MemberDAO;
import model.MemberVO;

public class UpdateController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		AccountVO avo = new AccountVO();
		
		
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("mvo");
		String id = mvo.getId();
		int total = mvo.getTotal();
		System.out.println("잔액 :" + total);
		
		
		//System.out.println("기존금액income:"+request.getParameter("beforeIncome"));
		String beforeIncome = request.getParameter("beforeIncome");
		String beforeSpend =request.getParameter("beforeSpend");
		
		
		
		String no = request.getParameter("no");
		String today = request.getParameter("today");
		String morningAfternoon = request.getParameter("morningAfternoon");
		String hh = request.getParameter("hh");
		String mm = request.getParameter("mm");
		String inAndOut = request.getParameter("inAndOut");
		String detail = request.getParameter("detail");
		int money = Integer.parseInt(request.getParameter("money"));

		avo.setNo(no);
		avo.setToday(today);
		avo.setType(inAndOut);
		avo.setDetail(detail);
		
		if(inAndOut.equals("income")){
			avo.setIncome(money);
			total -= Integer.parseInt(beforeIncome);
			total += money;
		}else if(inAndOut.equals("spend")){
			avo.setSpend(money);
			total += Integer.parseInt(beforeSpend);
			total -= money;
		}
		
		System.out.println("바뀐 total :" + total);
		mvo.setTotal(total);
		mvo.setId(id);
		MemberDAO.getInstance().updateMember(mvo);
		
		
		if(morningAfternoon.equals("am")){
			today = today + hh+":"+mm+":"+"00";
			avo.setTime(hh+":"+mm+":"+"00");
		}else if(morningAfternoon.equals("pm")){
			if(hh.equals("12")){
				hh = ""+(Integer.parseInt(hh)+11);
			}else{
				hh = ""+(Integer.parseInt(hh)+12);
			}
			today = today + hh+":"+mm+":"+"00";
			avo.setTime(hh+":"+mm+":"+"00");
		}
		
		AccountDAO.getInstance().updateDetail(avo);

		request.setAttribute("today", today);	
		
		return  "account/popup_result.jsp";
	}

}
