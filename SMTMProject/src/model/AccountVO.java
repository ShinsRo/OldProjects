package model;

public class AccountVO {
   private int inAndOut;
   private String no;
   private String today;
   private String time;
   private String detail;
   public AccountVO() {
      super();
   }
   public AccountVO(int inAndOut, String no, String today, String time, String detail) {
      super();
      this.inAndOut = inAndOut;
      this.no = no;
      this.today = today;
      this.time = time;
      this.detail = detail;
   }
   public int getInAndOut() {
      return inAndOut;
   }
   public void setInAndOut(int inAndOut) {
      this.inAndOut = inAndOut;
   }
   public String getNo() {
      return no;
   }
   public void setNo(String no) {
      this.no = no;
   }
   public String getToday() {
      return today;
   }
   public void setToday(String today) {
      this.today = today;
   }
   public String getTime() {
      return time;
   }
   public void setTime(String time) {
      this.time = time;
   }
   public String getDetail() {
      return detail;
   }
   public void setDetail(String detail) {
      this.detail = detail;
   }
   @Override
   public String toString() {
      return "AccountVO [inAndOut=" + inAndOut + ", no=" + no + ", today=" + today + ", time=" + time + ", detail="
            + detail + "]";
   }
   
   
}