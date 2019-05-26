package model;

import java.util.ArrayList;

public class AccountMockDAO {
	private static AccountMockDAO instance = new AccountMockDAO();
	
	private AccountMockDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static AccountMockDAO getInstace(){
		return instance;
	}
	ArrayList<DayVO> getAllDayList(){
		ArrayList<DayVO> list = new ArrayList<>();
		list.add(new DayVO("3/15", 30000, 10000));
		list.add(new DayVO("3/16", 0, 20000));
		list.add(new DayVO("3/13", 10000, 0));
		return list;
	}
	
	ArrayList<AccountVO> getAccountList(String today){
		ArrayList<AccountVO> list = new ArrayList<>();
		//list.add(new AccountVO(10000,0, "1", "3/15", "11:00:00", "만원짜리 물건"));
	 /*   list.add(new AccountVO(10000,0,"2", "3/15", "15:00:00", "삼만원짜리 월급"));
	    list.add(new AccountVO(10000,0, "3", "3/16", "16:00:00", "이만원짜리 물건"));
	    list.add(new AccountVO(10000,30000, "4", "3/13", "09:00:00", "만원짜리 일당"));*/
		return list;
	}
	void delete(String no){
		return;
	}
	void update(String no){
		return;
	}
	void add(AccountVO vo){
		return;
	}
}
