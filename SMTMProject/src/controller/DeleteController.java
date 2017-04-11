package controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccountDAO;
import model.MemberDAO;
import model.MemberVO;

public class DeleteController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("mvo");
		
		String no = request.getParameter("no");

		String id = mvo.getId();
		int total = mvo.getTotal();
		
		System.out.println("잔액 :" + total);
		
		
		//System.out.println("기존금액income:"+request.getParameter("beforeIncome"));
		String beforeIncome = request.getParameter("beforeIncome");
		String beforeSpend =request.getParameter("beforeSpend");
		
		total -= Integer.parseInt(beforeIncome);
		total += Integer.parseInt(beforeSpend);
		mvo.setTotal(total);
		MemberDAO.getInstance().updateMember(mvo);
		
		AccountDAO.getInstance().deleteDetail(no, id);
		return null;
	}

}
