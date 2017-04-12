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
	   
	   ArrayList<BoardVO> getAllListByPageNO(int pageNO, int boardSize) throws SQLException{
		   ArrayList<BoardVO> list = new ArrayList<>();
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				con = getConnection();
				StringBuilder sql = new StringBuilder();
				sql.append("select A.* from (");
				sql.append("select row_number() over(order by no desc) as rnum,");
				sql.append("board_no, title, to_char(time_posted, 'YY.MM.DD') as time_posted, id ");
				sql.append("from free_board");
				sql.append(") A where rnum between ? and ?");

				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, (pageNO-1)*boardSize+1);
				pstmt.setInt(2, pageNO*boardSize);
				rs = pstmt.executeQuery();
				while (rs.next()){
					list.add(new BoardVO(rs.getInt(1), rs.getString(2),
							null, rs.getString(3), rs.getString(4), null));
				}
			} finally {
				closeAll(rs, pstmt, con);;
			}
			return list;
		}
	   
	   BoardVO getDetail(int no) throws SQLException{
			BoardVO vo = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = getConnection();
				String sql = "select board_no, title, id, content, "
						+ "to_char(time_posted, 'YYYY/MM/DD HH24:MI:SS') " 
						+ "from free_board where no=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, no);
				rs = pstmt.executeQuery();
				if (rs.next())
					vo = new BoardVO(rs.getInt(1),
							rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), new ArrayList<CommentVO>());
				
				closeAll(rs, pstmt, null);
				int board_no = vo.getBoardNO();
				
				sql = "select com_no, content, depth, id parrent_com_no "
						+ "from board_comment where board_no = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, board_no);
				rs = pstmt.executeQuery();
				while(rs.next()){
					vo.getCommentList().add(
							new CommentVO(rs.getInt(1),
							rs.getString(2), rs.getInt(3), rs.getInt(4)));
				}
			} finally {
				closeAll(rs, pstmt, con);
			}
			return vo;
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
