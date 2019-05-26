package model;

public class DayVO {
   private String today;
   private int totalIncome;
   private int totalSpend;
   
   public DayVO() {
	super();
}
   
public DayVO(String today, int totalIncome, int totalSpend) {
	super();
	this.today = today;
	this.totalIncome = totalIncome;
	this.totalSpend = totalSpend;
}

public String getToday() {
      return today;
   }
   public void setToday(String today) {
      this.today = today;
   }
   public int getTotalIncome() {
      return totalIncome;
   }
   public void setTotalIncome(int totalIncome) {
      this.totalIncome = totalIncome;
   }
   public int getTotalSpend() {
      return totalSpend;
   }
   public void setTotalSpend(int totalSpend) {
      this.totalSpend = totalSpend;
      
   }

@Override
public String toString() {
	return "DayVO [today=" + today + ", totalIncome=" + totalIncome + ", totalSpend=" + totalSpend + "]";
}
   
}