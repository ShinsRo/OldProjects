package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDAO {
	   private String url, user, password;
	   private static BoardDAO instance = new BoardDAO();

	   // private DataSource dataSource;
	   private BoardDAO() {
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

	   public static BoardDAO getInstance() {
	      return instance;
	   }

	   private Connection getConnection() throws SQLException {
	      // connection을 생성하는 것이 아니라 connection을 dbcp로부터 빌려온다
	      // return dataSource.getConnection();
	      return DriverManager.getConnection(url, user, password);
	   }
	   
	   public ArrayList<BoardVO> getAllList(PagingBean p) throws SQLException{
           BoardVO bvo = null;
            ArrayList<BoardVO> list = new ArrayList<>();
           Connection con = null;
           PreparedStatement pstmt = null;
           ResultSet rs = null;
           try {
              con = getConnection();
              StringBuilder sql = new StringBuilder();
              sql.append("select A.* from (");
              sql.append("select row_number() over(order by board_no desc) as rnum,");
              sql.append("board_no, title, to_char(time_posted, 'YY.MM.DD') as time_posted, id ");
              sql.append("from free_board");
              sql.append(") A where rnum between ? and ?");

              pstmt = con.prepareStatement(sql.toString());
              pstmt.setInt(1, p.getStartRowNumber());
              pstmt.setInt(2, p.getEndRowNumber());
              rs = pstmt.executeQuery();
              while (rs.next()){
                 bvo = new BoardVO();
                 bvo.setBoardNO(rs.getInt("board_no"));
                 bvo.setTitle(rs.getString("title"));
                 bvo.setTimePosted(rs.getString("time_posted"));
                 bvo.setId(rs.getString("id"));
                 list.add(bvo);
              }
           } finally {
              closeAll(rs, pstmt, con);
           }
           return list;
        }
	   
	   public BoardVO getDetail(int no) throws SQLException{
	         BoardVO vo = null;
	         CommentVO cvo = null;
	         Connection con = null;
	         PreparedStatement pstmt = null;
	         ResultSet rs = null;
	         
	         try {
	            con = getConnection();
	            String sql = "select board_no, title, id, content, "
	                  + "to_char(time_posted, 'YYYY/MM/DD HH24:MI:SS') as time_posted " 
	                  + "from free_board where board_no=?";
	            pstmt = con.prepareStatement(sql);
	            pstmt.setInt(1, no);
	            rs = pstmt.executeQuery();
	            if (rs.next()){
	               vo = new BoardVO();
	               vo.setBoardNO(rs.getInt("board_no"));
	               vo.setTitle(rs.getString("title"));
	               vo.setId(rs.getString("id"));
	               vo.setContent(rs.getString("content"));
	               vo.setTimePosted(rs.getString("time_posted"));
	               vo.setCommentList(new ArrayList<CommentVO>());
	            }
	            
	            closeAll(rs, pstmt, null);
	            int board_no = vo.getBoardNO();
	            
	            sql = "select com_no, content, depth, id, parrent_com_no "
	                  + "from board_comment where board_no = ?";
	            pstmt = con.prepareStatement(sql);
	            pstmt.setInt(1, board_no);
	            rs = pstmt.executeQuery();
	            while(rs.next()){
	               cvo = new CommentVO();
	               cvo.setComNO(rs.getInt("com_no"));
	               cvo.setContent(rs.getString("content"));
	               cvo.setDepth(rs.getInt("depth"));
	               cvo.setId(rs.getString("id"));
	               cvo.setParrentComNO(rs.getInt("parrent_com_no"));
	               vo.getCommentList().add(cvo);
	            }
	         } finally {
	            closeAll(rs, pstmt, con);
	         }
	         return vo;
	      }
		public void insert(BoardVO vo) throws SQLException{
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = getConnection();
				String sql = "insert into free_board(board_no, title, id, content, time_posted) "
						+ "values(board_seq.nextval,?,?,?,sysdate)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getId());
				pstmt.setString(3, vo.getContent());
				pstmt.executeUpdate();
			}finally {
				closeAll(pstmt, con);
			}
			return ;
		}
		public int nextSeq() throws SQLException{
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int no = -1;
			try {
				con = getConnection();
				String sql = "select board_seq.nextval from dual";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next())
					no = rs.getInt(1);
			}finally {
				closeAll(rs, pstmt, con);
			}
			return no;
		}
		public void delete(int boardNO) throws SQLException {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = getConnection();
				String sql = "delete from board_comment where board_no =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, boardNO);
				pstmt.executeUpdate();
				
				closeAll(pstmt, null);
				
				sql = "delete from free_board where board_no=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, boardNO);
				pstmt.executeUpdate();
			} finally {
				closeAll(pstmt, con);
			}
			return;
		}
		  public int getTotalContents() throws SQLException {
		      Connection con=null;
		      PreparedStatement pstmt=null;
		      ResultSet rs=null;
		      int count=0;
		      try{
		         con=getConnection();
		         String sql="select count(*) from free_board";
		         pstmt=con.prepareStatement(sql);
		         rs=pstmt.executeQuery();
		         if(rs.next())
		            count=rs.getInt(1);
		      }finally {
		         closeAll(rs, pstmt, con);
		      }
		      return count;
		   }
			public void boardUpdate(int boardNO, String title, String content) throws SQLException {
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				try {
					con = getConnection();
					String sql = "update free_board " + "set title = ?, content = ? where board_no=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, title);
					pstmt.setString(2, content);
					pstmt.setInt(3, boardNO);
					
					pstmt.executeUpdate();
				} finally {
					closeAll(rs, pstmt, con);
				}
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
	   
}
