package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccountDAO;
import model.AccountVO;
import model.MemberDAO;
import model.MemberVO;

public class AddController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		
		
		
		AccountVO avo = new AccountVO();
		
		
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("mvo");
		String id = mvo.getId();
		int total = mvo.getTotal();
		System.out.println("사용자가 입력한 잔액 :" + total);
		//----------------------------------------------------------------------
		/*
		 *	MemberDAO에서 getTotal가져와 --사용자가 입력한 잔액
		 *	add할 때, 사용자의 income or spend를 total에 누적
		 *	update/delete할때도3.
		*/
		
		
		String detail = request.getParameter("detail");
		int money = Integer.parseInt(request.getParameter("money"));
		
		// inAndOut : 수입,지출type 
		String inAndOut = request.getParameter("inAndOut");
		String today = request.getParameter("today");
		
		avo.setToday(today);
		avo.setDetail(detail);
		avo.setType(inAndOut);
		
		// 시간
		String morningAfternoon = request.getParameter("morningAfternoon");
		String hh = request.getParameter("hh");
		String mm = request.getParameter("mm");
		
		if(morningAfternoon.equals("am")){
			today = today + hh+"/"+mm+"/"+"/00";
			avo.setTime(hh+"/"+mm+"/"+"00");
		}else if(morningAfternoon.equals("pm")){
			if(hh.equals("12")){
				hh = ""+(Integer.parseInt(hh)+11);
			}else{
				hh = ""+(Integer.parseInt(hh)+12);
			}
			
			today = today + hh+"/"+mm+"/"+"/00";
			avo.setTime(hh+"/"+mm+"/"+"00");
		}
		
		if(inAndOut.equals("income")){
			avo.setIncome(money);
			total += money;
			
			
		}else if(inAndOut.equals("spend")){
			avo.setSpend(money);
			total -= money;
		}
		
		System.out.println("바뀐 total :" + total);
		mvo.setTotal(total);
		System.out.println(mvo.toString());
		MemberDAO.getInstance().updateMember(mvo);
		
		
		AccountDAO.getInstance().insertDetail(avo, id);
		//System.out.println("AddController" + avo.toString());
		request.setAttribute("today", today);	
		
		return  "account/popup_result.jsp";
	}
}