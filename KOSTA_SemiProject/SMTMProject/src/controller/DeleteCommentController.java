package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.SessionExpiredException;
import model.BoardDAO;

public class DeleteCommentController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		if(session==null||session.getAttribute("mvo")==null){
			throw new SessionExpiredException();
		}
		int comNO = Integer.parseInt(request.getParameter("comNO"));
		String boardNO = request.getParameter("boardNO");
		BoardDAO dao = BoardDAO.getInstance();
		
		dao.deleteComment(comNO);
		
		return "redirect:DispatcherServlet?command=boardDetail&boardNO="+boardNO;
	}

}
