package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.SessionExpiredException;
import model.BoardDAO;

public class BoardDeleteController implements Controller {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   HttpSession session = request.getSession();
		if(session==null||session.getAttribute("mvo")==null){
			throw new SessionExpiredException();
		}
      BoardDAO dao = BoardDAO.getInstance();
      dao.delete(Integer.parseInt(request.getParameter("boardNO")));
      
      return "DispatcherServlet?command=board";
   }

}