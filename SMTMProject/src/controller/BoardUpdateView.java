package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BoardDAO;
import model.BoardVO;

public class BoardUpdateView implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int boardNO=Integer.parseInt(request.getParameter("boardNO"));
		HttpSession session = request.getSession(false);
		String path = null;
		if(session!=null){
			BoardVO vo = BoardDAO.getInstance().getDetail(boardNO);
			request.setAttribute( "bvo", vo);
			path = "/board/board_update.jsp";
		}else{
			path = "redirect:index.jsp";
		}
		return path;
	}

}
