package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccountDAO;
import model.AccountVO;
import model.MemberVO;

public class AddController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		AccountVO avo;
		
		/*HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("dao");
		String id = mvo.getId();*/
		String id = "java";
		
		String detail = request.getParameter("detail");
		int money = Integer.parseInt(request.getParameter("money"));
		// inAndOut : 수입,지출type 
		String inAndOut = request.getParameter("inAndOut");
		
		
		if(inAndOut.equals("income")){
			avo = new AccountVO(money,0,detail,inAndOut);
		}else{
			avo = new AccountVO(0,money,detail,inAndOut);
		}
			
		AccountDAO.getInstance().insertDetail(avo, id);

		return null;
	}
}