package controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import model.AccountDAO;
import model.CalendarBean;
import model.CalendarManager;
import model.DayVO;
import model.MemberVO;

public class GetGraphController implements Controller {

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      String m = null;
      //HttpSession session = request.getSession();
      PrintWriter pw = response.getWriter();
      //MemberVO vo = (MemberVO)session.getAttribute("mvo");
   
      int weekInfo = Integer.parseInt(request.getParameter("week"));
      Calendar gCal = GregorianCalendar.getInstance();
      //String id = "qwer";
      
      //HashMap<String,DayVO> map = AccountDAO.getInstance().getAllDayList(id);
       //String month = map.keySet().iterator().next().substring(7,9);
        
        CalendarBean cb = CalendarManager.getInstance().getCurrent();
        
        // 월 정보 넘기기
        request.setAttribute("month", m);
        
        ArrayList<DayVO> list = new ArrayList<DayVO>();
        list = cb.getListOnMonth();
        ArrayList<DayVO> weekList = new ArrayList<>();
       // fd 시작요일 정보
        int fd = cb.getFirstDayOfMonth();
        
        switch(weekInfo){
        case 1:
           // fd가 0일 경우 1주차 1~7
           // fd가 1일 경우 1주차 1~6
           for(int i = 1 ; i < 8-fd; i ++){
             if(cb.getListOnMonth().get(i) == null){
                
                // 년도을 동적으로 하기 위한 코드 필요
                weekList.add(new DayVO(gCal.get(Calendar.YEAR)+"/0"+cb.getMonth()+"/0"+i,0,0));      
             }else{
                weekList.add(cb.getListOnMonth().get(i));
             }
          }
          break;
        case 2:
           // 6 2~8
           // fd가 0일 경우 2주차 8~14
           // fd가 1일 경우 2주차 7~13
           for(int i = 8-fd; i< 7-fd+8*(weekInfo-1);i++){
             if(cb.getListOnMonth().get(i) == null){
                weekList.add(new DayVO(gCal.get(Calendar.YEAR)+"/0"+cb.getMonth()+"/0"+i,0,0));
             }else{     
                weekList.add(cb.getListOnMonth().get(i));
             }
          }
           break;
           
        case 3:
           // 6 9 ~ 15
          // fd가 0일 경우 3주차 15~21
          // fd가 1일 경우 3주차 14~20
           for(int i = 7-fd+8*(weekInfo-2); i < 6-fd+8*(weekInfo-1);i++){
             if(cb.getListOnMonth().get(i) == null){
                weekList.add(new DayVO(gCal.get(Calendar.YEAR)+"/0"+cb.getMonth()+"/0"+i,0,0));
             }else{
                weekList.add(cb.getListOnMonth().get(i));
           }
           }
           break;
        case 4:
          // 6 16 ~ 22
          // fd가 0일 경우 4주차 22~28
          // fd가 1일 경우 4주차 21~27
           for(int i = 6-fd+8*(weekInfo-2); i < 5-fd+8*(weekInfo-1);i++){
             if(cb.getListOnMonth().get(i) == null){
                weekList.add(new DayVO(gCal.get(Calendar.YEAR)+"/0"+cb.getMonth()+"/0"+i,0,0));
             }else{
                weekList.add(cb.getListOnMonth().get(i));
             }
          }
           break;
        case 5:
        	int flag = cb.getLastDayOfMonth()+1;        
            if(fd == 6){
               flag = 4-fd+8*(weekInfo-1);
            }
           // 6 23 ~ 29
           // fd가 0일 경우 마지막 주차 29 ~ 마지막 6일 경우 예외
           for(int i =5-fd+8*(weekInfo-2); i < 4-fd+8*(weekInfo-1);i++ ){
             if(cb.getListOnMonth().get(i) == null){
                weekList.add(new DayVO(gCal.get(Calendar.YEAR)+"/0"+cb.getMonth()+"/0"+i,0,0));
             }else{
                weekList.add(cb.getListOnMonth().get(i));
             }
          }
           break;
        case 6:
           // 6 30~ last
           for(int i = 4-fd+8*(weekInfo-2); i <= cb.getLastDayOfMonth();i++){
             if(cb.getListOnMonth().get(i) == null){
                weekList.add(new DayVO(gCal.get(Calendar.YEAR)+"/0"+cb.getMonth()+"/0"+i,0,0));
             }else{
                weekList.add(cb.getListOnMonth().get(i));
             }
           }
           break;
        }
        
        JSONArray json = new JSONArray(weekList);
      pw.println(json.toString());
      pw.close();
      return null;
   }
}