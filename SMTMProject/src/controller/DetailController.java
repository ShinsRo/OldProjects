package controller;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import exception.SessionExpiredException;
import model.AccountDAO;
import model.AccountVO;
import model.MemberVO;

public class DetailController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		if(session==null||session.getAttribute("mvo")==null){
			throw new SessionExpiredException();
		}
		response.setContentType("text/html;charset=utf-8");

		PrintWriter pw = response.getWriter();
		ArrayList<AccountVO> list = new ArrayList<AccountVO>();
		MemberVO vo = (MemberVO) session.getAttribute("mvo");
		
		
		String id = vo.getId();

		String today = request.getParameter("today");

		list = AccountDAO.getInstance().getDetailList(today, id);

		JSONArray js = new JSONArray(list);
		System.out.println(js.toString());
		pw.println(js.toString());

		pw.close();
		return null;
	}
}