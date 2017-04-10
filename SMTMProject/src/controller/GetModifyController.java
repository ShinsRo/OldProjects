package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MemberDAO;
import model.MemberVO;

public class GetModifyController implements Controller {
   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session=request.getSession(false);
      if(session==null||session.getAttribute("mvo")==null){
         return "login.jsp";
      }
      String password=request.getParameter("password");
      String id = request.getParameter("id");
      String name=request.getParameter("name");
      int limit=Integer.parseInt(request.getParameter("limit"));
     
      MemberVO vo=new MemberVO();
      vo.setPassword(password);
      vo.setId(id);
      vo.setName(name);
      vo.setLimit(limit);
      
      session.setAttribute("mvo", vo);
      MemberDAO.getInstance().updateMember(vo);
      return "/member/modify_result.jsp";
   }
}