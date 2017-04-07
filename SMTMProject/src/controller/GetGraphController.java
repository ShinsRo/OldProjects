package controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import model.AccountDAO;
import model.DayVO;
import model.MemberVO;

public class GetGraphController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();
		MemberVO vo = (MemberVO)session.getAttribute("mvo");
	
		String weekInfo = request.getParameter("week");
		
		HashMap<String,DayVO> map = AccountDAO.getInstance().getAllDayList(vo.getId());
		 map=AccountDAO.getInstance().getAllDayList(vo.getId());
	     ArrayList<DayVO> list = new ArrayList<DayVO>();

	     System.out.println(weekInfo);
	     //System.out.println(map.get("key20170101"));
	    //list.add(map.get("key20170101"));
	     
	     if(weekInfo.equals("1")){
	    	for(int i = 1;i<8;i++){
	    		list.add(map.get("key2017010"+i));
	    	}
	    }else if(weekInfo.equals("2")){
	    	for(int i = 8;i<15;i++){
	    		if(i>9){
	    			list.add(map.get("key201701"+i));
	    		}else{
		    		list.add(map.get("key2017010"+i));
	    		}
	    	}
	    }else if(weekInfo.equals("3")){
	    	for(int i = 15;i<22;i++){
	    		list.add(map.get("key201701"+i));
	    	}
	    }else{
	    	for(int i = 22;i<29;i++){
	    		list.add(map.get("key201701"+i));
	    	}
	    }
	      
	/*	for(int i = 1;i<8;i++){
			map2.put("key2017010"+i, map.get("key2017010"+i));
		}*/
	    System.out.println(list.toString());
		JSONArray json = new JSONArray(list);
		System.out.println(json.toString());
		pw.println(json.toString());
		pw.close();
		return null;
	}
}