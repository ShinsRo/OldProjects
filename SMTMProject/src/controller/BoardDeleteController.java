package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardDAO;

public class BoardDeleteController implements Controller {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      BoardDAO dao = BoardDAO.getInstance();
      dao.delete(Integer.parseInt(request.getParameter("boardNO")));
      
      return "DispatcherServlet?command=board";
   }

}