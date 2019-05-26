package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.SessionExpiredException;
import model.BoardDAO;
import model.BoardVO;
import model.MemberVO;

public class WriteController implements Controller {

   @Override
   public String execute(HttpServletRequest request,
         HttpServletResponse response) throws Exception {
	   HttpSession session = request.getSession();
	   if(session==null||session.getAttribute("mvo")==null){
		   throw new SessionExpiredException();
	   }
      String title = request.getParameter("title");
      String content = request.getParameter("content");
      String path="redirect:DispatcherServlet?command=board";
      String id = ((MemberVO)session.getAttribute("mvo")).getId();
      BoardVO vo = new BoardVO(0,title,content,null,id,null); 
      BoardDAO.getInstance().insert(vo);
      
      
      return path;
   }

}