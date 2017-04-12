package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BoardDAO;
import model.BoardVO;
import model.MemberVO;

public class WriteController implements Controller {

   @Override
   public String execute(HttpServletRequest request,
         HttpServletResponse response) throws Exception {
      String title = request.getParameter("title");
      String content = request.getParameter("content");
      //String id = request.getParameter("id");
      HttpSession session = request.getSession(false);
      String path="redirect:DispatcherServlet?command=board";
      if(session == null){
         path = "redirect:index.jsp";
      }
      String id = ((MemberVO)session.getAttribute("mvo")).getId();
      //BoardVO(String title, String writer, String password, String content)
      BoardVO vo = new BoardVO(0,title,content,null,id,null);   
      /*for(i=0; i<31; i++)*/
      BoardDAO.getInstance().insert(vo);
      
      
      return path;
   }

}