package controller;

public class HandlerMapping {
	private static HandlerMapping instance=new HandlerMapping();
	private HandlerMapping(){}
	public static HandlerMapping getInstance(){
		return instance;
	}
	public Controller create(String command){
		Controller c=null;
		if(c.equals("login")){
			c = new LoginController();
		}else if(c.equals("logout")){
			c = new LogOutController();
		}else if(c.equals("register")){
			c = new RegisterController();
		}else if(c.equals("getAll")){
			c = new GetAllListController();
		}else if(c.equals("detail")){
			c = new DetailController();
		}else if(c.equals("add")){
			c = new AddController();
		}else if(c.equals("update")){
			c = new UpdateController();
		}else if(c.equals("delete")){
			c = new DeleteController();
		}
		return c;
	}
}










