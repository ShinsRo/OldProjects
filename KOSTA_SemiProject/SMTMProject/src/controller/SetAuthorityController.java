package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.SessionExpiredException;
import model.MemberDAO;


public class SetAuthorityController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		if(session==null||session.getAttribute("mvo")==null){
			throw new SessionExpiredException();
		}
		response.setContentType("text/html; charset=utf-8");
		int authoNum = Integer.parseInt(request.getParameter("authoNum"));
		String id = request.getParameter("id");
		System.out.println(id);
		MemberDAO.getInstance().updateAuthority(authoNum, id);
		return null;
	}

}
