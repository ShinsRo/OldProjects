package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountDAO {
   private String url, user, password;
   private static AccountDAO instance = new AccountDAO();

   // private DataSource dataSource;
   private AccountDAO() {
      url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
      user = "scott";
      password = "tiger";
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
      } catch (ClassNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      // dataSource = DataSourceManager.getInstance().getDataSource();
   }

   public static AccountDAO getInstance() {
      return instance;
   }

   public Connection getConnection() throws SQLException {
      // connection을 생성하는 것이 아니라 connection을 dbcp로부터 빌려온다
      // return dataSource.getConnection();
      return DriverManager.getConnection(url, user, password);
   }

   public void closeAll(PreparedStatement pstmt, Connection con) throws SQLException {
      closeAll(null, pstmt, con);
   }

   public void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) throws SQLException {
      if (rs != null)
         rs.close();
      if (pstmt != null)
         pstmt.close();
      if (con != null)
         con.close();
   }

   // Mock엔 매개변수가 없는데 id추가함
   // account_member에서 name
   // account_book에서 no,today,detail, inAndOut, id가져옴
   /**
    * getListController에서 리스트 불러올때 사용
    * @param id
    * @return
    * @throws SQLException
    */
   public HashMap<String,DayVO> getAllDayList(String id) throws SQLException {
         HashMap<String, DayVO> list = new HashMap<String, DayVO>();
         StringBuilder sql = new StringBuilder();
         // String tmp = "";
         Connection con = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         try {
            con = getConnection();
            sql.append("select m.name,m.total,b.no, ");
            // total가져와야하는지 잘 모르겟엉
            sql.append("to_char(b.today,'yyyy/mm/dd') as today,b.detail,b.income,b.spend,b.id ");
            sql.append("from ACCOUNT_MEMBER m, ACCOUNT_BOOK b ");
            sql.append("where m.id = b.id and m.id=?");
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            int incomeTotal = 0;
            int spendTotal = 0;
            String temp = "";
            DayVO dvo = new DayVO();
            while (rs.next()) {

               String today = rs.getString("today");// 17/04/03
               today.replace("/", "");
               String key = "key"+today.replace("/", "");;
               if (list.get(key) == null) {
                  dvo.setToday(today);
                  dvo.setTotalIncome(incomeTotal);
                  dvo.setTotalSpend(spendTotal);

                  // list.add(dvo);
                  list.put(key, dvo);
                  temp = today;

                  dvo = new DayVO();
                  list.get(key).setTotalIncome(rs.getInt("income"));
                  list.get(key).setTotalSpend(rs.getInt("spend"));
                  incomeTotal = 0;
                  spendTotal = 0;
               } else {
                  list.get(key).setTotalIncome(list.get(key).getTotalIncome() + rs.getInt("income"));
                  list.get(key).setTotalSpend(list.get(key).getTotalSpend() + rs.getInt("spend"));
                  // spendTotal += rs.getInt("spend");
               }

            }

         } finally {
            closeAll(rs, pstmt, con);
         }
         return list;
      }
   /**
    * DetailController에서 그날에 대한 상세 수입/지출내역 불러오기
    * @param today
    * @return
    * @throws SQLException 
    */
   public ArrayList<AccountVO> getDetailList(String today,String id) throws SQLException{
      ArrayList<AccountVO> list = new ArrayList<AccountVO>();
      StringBuilder sql = new StringBuilder();
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try{
         con = getConnection();
         sql.append("select m.name, ");
         sql.append("b.no,to_char(b.today,'yyyy/mm/dd') as today,to_char(b.today,'HH24:MI:SS') as time,b.detail,b.income,b.spend,b.id ");
         sql.append("from ACCOUNT_MEMBER m, ACCOUNT_BOOK b ");
         sql.append("where m.id = b.id and m.id=? and to_char(b.today,'yyyy/mm/dd')=?");
         pstmt = con.prepareStatement(sql.toString());
         pstmt.setString(1, id);
         pstmt.setString(2, today);
         rs = pstmt.executeQuery();
         while(rs.next()){
            AccountVO avo = new AccountVO();
            avo.setNo(rs.getString("no"));
            avo.setToday(rs.getString("today"));
            avo.setTime(rs.getString("time"));
            avo.setIncome(rs.getInt("income"));
            avo.setSpend(rs.getInt("spend"));
            avo.setDetail(rs.getString("detail"));
            list.add(avo);
            //avo.set
         }
      }finally{
         closeAll(rs,pstmt,con);
      }
      return list;
      
   }
   /**
    * vo안에 no,today, type, detail, income or spend
    * @param vo
    * @param id
    * @throws SQLException
    */
   public void insertDetail(AccountVO vo, String id) throws SQLException {
      StringBuilder sql = new StringBuilder();
      Connection con = null;
      PreparedStatement pstmt = null;
      String type = vo.getType();
      try {
         con = getConnection();
         if(type.equals("income")){
            sql.append("insert into ACCOUNT_BOOK(no, today, income, detail, id) "); 
            sql.append("values(account_seq.nextval, sysdate, ?, ?, ?)"); 
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, vo.getIncome());
            
         }else if(type.equals("spend")){
            sql.append("insert into ACCOUNT_BOOK(no, today, spend, detail, id) "); 
            sql.append("values(account_seq.nextval, sysdate, ?, ?, ?)"); 
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, vo.getSpend());
         }
         pstmt.setString(2, vo.getDetail());
         pstmt.setString(3, id);
         

         pstmt.executeQuery();
      } finally {
         closeAll(pstmt, con);
      }
   }
   /**
    * vo안에 no, type, income or spend, detail, today(고정된값), time(사용자가 selct로 입력한 값)
    * @param vo
    * @param id
    * @throws SQLException 
    */
   public void updateDetail(AccountVO vo, String id) throws SQLException{
      StringBuilder sql = new StringBuilder();
      Connection con = null;
      PreparedStatement pstmt = null;
      String todayAndTime = vo.getToday()+vo.getTime();
      //vo.getToday() : 2017/04/06
      //vo.getTime() : 9:00:15
      String type = vo.getType();
      try {
         con = getConnection();
         //update ACCOUNT_BOOK set today=to_date('2017/1/1 9:12:10', 'yyyy/mm/dd hh24:mi:ss'), 
         //spend=5000,detail='애비로드' where no=1 and id='java'
         //update account_book set today=to_date(todayAndTime,'yyyy/mm/dd hh24:mi:ss'),income=?,detail=? where no=? and id=?
         if(type.equals("income")){
            sql.append("update account_book set today=to_date(?,'yyyy/mm/dd hh24:mi:ss'), ");
            sql.append("income=?,detail=? ");
            sql.append("where no=? and id=?");
            pstmt = con.prepareStatement(sql.toString());
            
            pstmt.setInt(2, vo.getIncome());
            
         }else if(type.equals("spend")){
            sql.append("update account_book set today=to_date(?,'yyyy/mm/dd hh24:mi:ss'), ");
            sql.append("income=?,detail=? ");
            sql.append("where no=? and id=?");
            pstmt = con.prepareStatement(sql.toString());
            
            pstmt.setInt(2, vo.getSpend());
         }
         pstmt.setString(1, todayAndTime);
         pstmt.setString(3, vo.getDetail());
         pstmt.setString(4, vo.getNo());
         pstmt.setString(5, id);

         pstmt.executeUpdate();
      } finally {
         closeAll(pstmt, con);
      }
   }
   
    public void deleteDetail(String no, String id) throws SQLException{
         StringBuilder sql = new StringBuilder();
         Connection con = null;
         PreparedStatement pstmt = null;
         try{
            con = getConnection();
            sql.append("delete from ACCOUNT_BOOK ");
            sql.append("where no=? and id=?");
            pstmt=con.prepareStatement(sql.toString());
            pstmt.setString(1, no);
            pstmt.setString(2, id);
            pstmt.executeQuery();
         }
         finally{
            closeAll(pstmt, con);
         }
      }
   public static void main(String[] args) {
      try {
         HashMap<String,DayVO> test = AccountDAO.getInstance().getAllDayList("java");
         ArrayList<AccountVO> detailTest = AccountDAO.getInstance().getDetailList("2017/04/06", "java");
         /*AccountVO avo = new AccountVO();
         avo.setType("spend");
         avo.setSpend(50000);
         avo.setDetail("유스페이스몰치킨정식");
         avo.setNo("11");
         avo.setToday("2017/04/07");
         avo.setTime("09:00:00");*/
         //AccountDAO.getInstance().insertDetail(avo, "java");
         //AccountDAO.getInstance().updateDetail(avo, "java");
         //System.out.println(test);
         //System.out.println(detailTest);
         AccountVO avo = new AccountVO();
         avo.setNo("12");
       //  AccountDAO.getInstance().deleteDetail(avo, "java");
      } catch (SQLException e) {
         e.printStackTrace();
      }

   }
   
   
}