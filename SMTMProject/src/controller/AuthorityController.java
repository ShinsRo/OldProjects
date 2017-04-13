package controller;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import exception.SessionExpiredException;
import model.MemberDAO;
import model.MemberVO;

public class AuthorityController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		if(session==null||session.getAttribute("mvo")==null){
			throw new SessionExpiredException();
		}
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		ArrayList<MemberVO> memList = MemberDAO.getInstance().getAllMemberList();
		 System.out.println(memList);
		JSONArray json = new JSONArray(memList);
		out.println(json.toString());
		out.close();
		return null;
	}

}
