package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberDAO;
import model.MemberVO;

public class IdCheckController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		boolean flag= MemberDAO.getInstance().idCheck(id);
		String message = null;
		String color = null;
		String url="member/idcheck.jsp";
		if(flag){
			message = id+" 사용불가!";
			request.setAttribute("flag", "");
		} 
		else{ 
			request.setAttribute("flag", id);
			message = id+" 사용가능";
		}
		request.setAttribute("message", message);
		
		return url;
	}
}
