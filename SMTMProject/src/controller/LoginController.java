package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MemberDAO;
import model.MemberVO;

public class LoginController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		MemberVO vo = MemberDAO.getInstance().login(id, password);
		HttpSession session = request.getSession();
		String path = "";
		if (vo != null) {
			if (vo.getAuthority() == 1) {
				session.setAttribute("administrator", vo);
				path = "redirect:member/admin.jsp";// 1이면 관리자
			} else {
				session.setAttribute("mvo", vo);
				path = "redirect:member/login_result.jsp";
			}
		} else {
			path = "redirect:member/login_fail.jsp";
		}
		return path;
	}
}