package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardDAO;

public class DeleteCommentController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int comNO = Integer.parseInt(request.getParameter("comNO"));
		String boardNO = request.getParameter("boardNO");
		BoardDAO dao = BoardDAO.getInstance();
		
		dao.deleteComment(comNO);
		
		return "redirect:DispatcherServlet?command=boardDetail&boardNO="+boardNO;
	}

}
