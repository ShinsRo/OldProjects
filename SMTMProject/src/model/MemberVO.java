package model;

public class MemberVO {
   private String id;
   private String name;
   private String password;
   private int total;
   public MemberVO() {
      super();
   }
   public MemberVO(String id, String name, String password, int total) {
      super();
      this.id = id;
      this.name = name;
      this.password = password;
      this.total = total;
   }
   public String getId() {
      return id;
   }
   public void setId(String id) {
      this.id = id;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getPassword() {
      return password;
   }
   public void setPassword(String password) {
      this.password = password;
   }
   public int getTotal() {
      return total;
   }
   public void setTotal(int total) {
      this.total = total;
   }
   public String toString() {
      return "MemberVO [id=" + id + ", name=" + name + ", password=" + password + ", total=" + total + "]";
   }
}