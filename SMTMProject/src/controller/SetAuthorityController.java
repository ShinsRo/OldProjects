package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberDAO;


public class SetAuthorityController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		int authoNum = Integer.parseInt(request.getParameter("authoNum"));
		String id = request.getParameter("id");
		System.out.println(id);
		MemberDAO.getInstance().updateAuthority(authoNum, id);
		// return "/member/admin.jsp";	
		return null;
	}

}
