package controller;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import model.MemberDAO;
import model.MemberVO;

public class AuthorityController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
