package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardDAO;
import model.BoardVO;

public class BoardUpdateController implements Controller {

   @Override
   public String execute(HttpServletRequest request,
         HttpServletResponse response) throws Exception {
      String title = request.getParameter("title");
      String content = request.getParameter("content");
      String id = request.getParameter("id");
      BoardVO vo=new BoardVO();
      vo.setTitle(title);
      vo.setContent(content);
      vo.setId(id);
      BoardDAO.getInstance().boardUpdate(vo.getBoardNO(), vo.getTitle(), vo.getContent());   

      String path="redirect:DispatcherServlet?command=board";
      return path;
   }

}