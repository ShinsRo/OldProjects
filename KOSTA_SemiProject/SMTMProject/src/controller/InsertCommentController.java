package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.SessionExpiredException;
import model.BoardDAO;
import model.CommentVO;
import model.MemberVO;

public class InsertCommentController implements Controller {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   HttpSession session = request.getSession();
		if(session==null||session.getAttribute("mvo")==null){
			throw new SessionExpiredException();
		}
      int parrentComNO = Integer.parseInt(request.getParameter("parrentComNO"));
      int boardNO = Integer.parseInt(request.getParameter("board_no"));
      String content = request.getParameter("content");
      String id = ((MemberVO)request.getSession().getAttribute("mvo")).getId();
      int depth = Integer.parseInt(request.getParameter("depth"));
      CommentVO vo = new CommentVO();
      vo.setParrentComNO(parrentComNO);
      vo.setBoardNO(boardNO);
      vo.setContent(content);
      vo.setId(id);
      vo.setDepth(depth);
      
      BoardDAO.getInstance().insertComment(vo);
      
      return "redirect:DispatcherServlet?command=boardDetail&boardNO="+boardNO;
   }
}