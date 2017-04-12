package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BoardDAO;
import model.BoardVO;

public class BoardDetailController implements Controller {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session=request.getSession(false);
      if(session==null||session.getAttribute("mvo")==null){
         return "redirect:index.jsp";
      }
      int boardNO=Integer.parseInt(request.getParameter("boardNO"));
      BoardVO vo = BoardDAO.getInstance().getDetail(boardNO);
      request.setAttribute("bvo", vo);
      return "board/board_detail.jsp";
   }
}