package controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AccountDAO;

public class DeleteController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*String no = request.getParameter("no");
		String id = request.getParameter("id");*/
		String id ="java";
		String no="26";
		AccountDAO.getInstance().deleteDetail(no, id);
		return "account/detail.jsp";
	}

}
