package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.SessionExpiredException;
import model.BoardDAO;
import model.BoardVO;

public class BoardUpdateController implements Controller {

   @Override
   public String execute(HttpServletRequest request,
         HttpServletResponse response) throws Exception {
	   HttpSession session = request.getSession();
		if(session==null||session.getAttribute("mvo")==null){
			throw new SessionExpiredException();
		}
	  int boardNO = Integer.parseInt(request.getParameter("boardNO"));
      String title = request.getParameter("title");
      String content = request.getParameter("content");
      String id = request.getParameter("writer");
      //System.out.println(boardNO);
      BoardDAO.getInstance().boardUpdate(boardNO,title,content);   

      String path="redirect:DispatcherServlet?command=board";
      return path;
   }

}