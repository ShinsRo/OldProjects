/*package model;

public class MemberMockDAO {
	//id : java , password : 1234
	private static MemberMockDAO instance = new MemberMockDAO();
	private MemberMockDAO() {
		
	}
	public static MemberMockDAO getInstance(){
		return instance;
	}
	public MemberVO login(String id, String password){
		
		if(this.idCheck(id)){
			if(password.equals("1234"))
				return new MemberVO("java", "SMTM", "1234", 40000);
		}
		return null;
	}
	public boolean idCheck(String id){
		if(id.equals("java")){
			return true;
		}
		return false;
	}
	public void insert(MemberVO vo){
		System.out.println("insert성공");
	}
	public void delete(String no){
		System.out.println("삭제성공");
	}
	public void upate(MemberVO vo){
		System.out.println(vo + "수정성공");
	}
}
*/