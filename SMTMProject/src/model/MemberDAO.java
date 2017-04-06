package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class MemberDAO {
   private static MemberDAO instance=new MemberDAO();
   private DataSource dataSource;
   //외부에서 생성못하게 private 처리 
   private MemberDAO(){
      dataSource = DataSourceManager.getInstance().getDataSource();
   }
   //외부에 현 객체레퍼런스를 공유 
   public static MemberDAO getInstance(){
      return instance;
   }
   public Connection getConnection() throws SQLException{
      //connection을 생성하는 것이 아니라 connection을 dbcp로부터 빌려온다
      return dataSource.getConnection();
   }
   public void closeAll(PreparedStatement pstmt,Connection con) throws SQLException{
      closeAll(null, pstmt,con);
   }
   public void closeAll(ResultSet rs,PreparedStatement pstmt,
         Connection con) throws SQLException{
      if(rs!=null)
         rs.close();
      if(pstmt!=null)
         pstmt.close();
      if(con!=null)
         con.close();
   }
   public MemberVO login(String id,String password) throws SQLException{
      MemberVO vo=new MemberVO();
      Connection con=null;
      PreparedStatement pstmt=null;
      ResultSet rs=null;    
      try{
         con = getConnection();
         String sql = "select name from account_member where id=? and password=?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, id);
         pstmt.setString(2, password);
         rs = pstmt.executeQuery();
         if(rs.next()){
            vo.setId(id);
            vo.setName(rs.getString("name"));
            vo.setTotal(0);//여기서 해줘야하나..?
            vo.setPassword(password);
         }
      }finally{
         closeAll(rs, pstmt, con);
      }
      return vo;
   }
   
   //회원가입 -> 입력한 아이디, 패스워드, 이름 insert 및 각 회원 테이블의 total에 0 삽입
   public void insert(MemberVO vo) throws SQLException {
      Connection con=null;
      PreparedStatement pstmt=null;
      try{
         con=getConnection();
         String sql="insert into account_member(id,password,name,total)"
               + " values(?,?,?,0)";
         pstmt=con.prepareStatement(sql);
         pstmt.setString(1, vo.getId());
         pstmt.setString(2, vo.getPassword());
         pstmt.setString(3, vo.getName());
         
         pstmt.executeQuery();
      }finally{
         closeAll(pstmt, con);
      }
   }
   //회원정보수정-update의 경우, 이름만 수정하게 해놈
   public void updateMember(MemberVO vo) throws SQLException{
      Connection con=null;
      PreparedStatement pstmt=null;
      try{
         con=getConnection();
         String sql=
            "update account_member set name=? where id=?";
         pstmt=con.prepareStatement(sql);         
         pstmt.setString(2, vo.getName());
         pstmt.setString(2, vo.getId());
         pstmt.executeUpdate();         
      }finally{
         closeAll(pstmt,con);
      }
   }
   
   public boolean idCheck(String id) throws SQLException{
      boolean flag=false;
      Connection con=null;
      PreparedStatement pstmt=null;
      ResultSet rs=null;
      try{
         con=getConnection();
         String sql="select count(*) from account_member where id=?";
         pstmt=con.prepareStatement(sql);
         pstmt.setString(1,id);
         rs=pstmt.executeQuery();
         if(rs.next()&&(rs.getInt(1)>0))
         flag=true;         
      }finally{
         closeAll(rs,pstmt,con);
      }
      return flag;
   }
   
   //MemberDAO엔 delete필요없는것같음!
   /*public void delete(String id){
      
   }
   */
   
   
}