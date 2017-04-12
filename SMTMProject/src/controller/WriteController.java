package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardDAO;
import model.BoardVO;

public class WriteController implements Controller {

   @Override
   public String execute(HttpServletRequest request,
         HttpServletResponse response) throws Exception {
      String title = request.getParameter("title");
      String content = request.getParameter("content");
      String id = request.getParameter("id");
      //BoardVO(String title, String writer, String password, String content)
      BoardVO vo = new BoardVO(0,title,content,null,id,null);   
      /*for(i=0; i<31; i++)*/
      BoardDAO.getInstance().insert(vo);
      
      String path="redirect:DispatcherServlet?command=board";
      return path;
   }

}