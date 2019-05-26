package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberDAO;
import model.MemberVO;

public class RegisterController implements Controller {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      String id = request.getParameter("id");
      String name = request.getParameter("name");
      String password = request.getParameter("password");
      int total = Integer.parseInt(request.getParameter("total"));
      int limit = Integer.parseInt(request.getParameter("limit"));
      MemberVO vo = new MemberVO();
      vo.setId(id);
      vo.setName(name);
      vo.setPassword(password);
      vo.setTotal(total);
      vo.setLimit(limit);
      MemberDAO.getInstance().insert(vo);
      return "member/register_result.jsp";
   }

}