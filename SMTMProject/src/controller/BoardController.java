package controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.SessionExpiredException;
import model.BoardDAO;
import model.BoardVO;
import model.ListVO;
import model.PagingBean;

public class BoardController implements Controller {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   HttpSession session = request.getSession();
		if(session==null||session.getAttribute("mvo")==null){
			throw new SessionExpiredException();
		}
      int total = BoardDAO.getInstance().getTotalContents();
      String no = request.getParameter("nowPage");
      
      //만약 board.jsp에서 현재 페이지 번호를 넘기면 넘겨받음
      int nowPage;
      if(no == null){
         nowPage = 1;
      }else{
         nowPage = Integer.parseInt(no);
      }
      PagingBean p = new PagingBean(total,nowPage);
      ArrayList<BoardVO> list = BoardDAO.getInstance().getAllList(p);
      ListVO listVO = new ListVO(list,p);
      request.setAttribute("listVO", listVO);
      return "board/board.jsp";
      
   }

}