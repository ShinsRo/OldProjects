package controller;

public class HandlerMapping {
	private static HandlerMapping instance=new HandlerMapping();
	private HandlerMapping(){}
	public static HandlerMapping getInstance(){
		
		return instance;
	}
	public Controller create(String command){
		Controller c=null;
		//System.out.println(command);
		if(command.equals("login")){
			c = new LoginController();
		}else if(command.equals("logout")){
			c = new LogOutController();
		}else if(command.equals("register")){
			c = new RegisterController();
		}else if(command.equals("getAllList")){
			c = new GetAllListController();
		}else if(command.equals("detail")){
			c = new DetailController();
		}else if(command.equals("add")){
			c = new AddController();
		}else if(command.equals("update")){
			c = new UpdateController();
		}else if(command.equals("delete")){
			c = new DeleteController();
		}else if(command.equals("idcheck")){
			c = new IdCheckController();
		}else if(command.equals("getCalendarList")){
			c = new GetCalendarListController();
		}else if(command.equals("getCurrent")){
			c = new GetCurrentController();
		}else if(command.equals("graph")){
	         c = new GetGraphController();
	    }else if(command.equals("modify")){
	          c = new  GetModifyController();
	    }
		return c;
	}
}










