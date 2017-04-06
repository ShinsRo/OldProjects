package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import model.AccountDAO;
import model.AccountVO;
import model.MemberVO;

public class UpdateController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		//String command = request.getParameter("command");
		AccountVO vo = new AccountVO();
		/*HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("dao");
		String id = mvo.getId();*/
		String id = "java";
		
		String no = request.getParameter("no");
		String today = request.getParameter("today");
		
	
		String morningAfternoon = request.getParameter("morningAfternoon");
		String hh = request.getParameter("hh");
		String mm = request.getParameter("mm");
		String inAndOut = request.getParameter("inAndOut");
		String detail = request.getParameter("detail");
		int money = Integer.parseInt(request.getParameter("money"));
		
		vo.setToday(today);
		vo.setType(inAndOut);
		
		if(inAndOut.equals("income")){
			vo.setIncome(money);
		}else if(inAndOut.equals("spend")){
			vo.setSpend(money);
		}
		
		
		if(hh.equals("am")){
			today = today + hh+"/"+mm+"/"+"/00";
			vo.setTime(hh+"/"+mm+"/"+"00");
		}else if(hh.equals("pm")){
			//today = request.getParameter("today") + hh+"/"+mm+"/"+"/00";
			hh = hh+12;
			today = today + hh+"/"+mm+"/"+"/00";
			vo.setTime(hh+"/"+mm+"/"+"00");
		}
		
		// * vo안에 no, type, income or spend, detail, today(고정된값), time(사용자가 selct로 입력한 값)
		AccountDAO.getInstance().updateDetail(vo, id);
		
		//System.out.println(morningAfternoon);
		return null;
	}

}
