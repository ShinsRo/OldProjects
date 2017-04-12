package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

public class MemberDAO {
	private static MemberDAO instance = new MemberDAO();
	private DataSource dataSource;

	// 외부에서 생성못하게 private 처리
	private MemberDAO() {
		dataSource = DataSourceManager.getInstance().getDataSource();
	}

	// 외부에 현 객체레퍼런스를 공유
	public static MemberDAO getInstance() {
		return instance;
	}

	public Connection getConnection() throws SQLException {
		// connection을 생성하는 것이 아니라 connection을 dbcp로부터 빌려온다
		return dataSource.getConnection();
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

	/**
	 * 4차 구현 - login시 authority도 가져옴 authority : default 0 -- 0 이면 일반회원, 1이면 관리자
	 * 
	 * @throws SQLException
	 **/

	public MemberVO login(String id, String password) throws SQLException {
		MemberVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select name, limit, total,authority from account_member where id=? and password=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new MemberVO();
				vo.setId(id);
				vo.setLimit(rs.getInt("limit"));
				vo.setName(rs.getString("name"));
				vo.setTotal(rs.getInt("total"));
				vo.setAuthority(rs.getInt("authority"));
				vo.setPassword(password);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return vo;
	}

	// 회원가입 -> 입력한 아이디, 패스워드, 이름 insert 및 각 회원 테이블의 total에 0 삽입
	public void insert(MemberVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			String sql = "insert into account_member(id,password,name,total,limit)" + " values(?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setInt(4, vo.getTotal());
			pstmt.setInt(5, vo.getLimit());
			pstmt.executeQuery();
		} finally {
			closeAll(pstmt, con);
		}
	}
	// 회원정보수정-update의 경우, 이름만 수정하게 해놈

	public boolean idCheck(String id) throws SQLException {
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select count(*) from account_member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next() && (rs.getInt(1) > 0))
				flag = true;
		} finally {
			closeAll(rs, pstmt, con);
		}
		return flag;
	}

	public int getInfo(String id) throws SQLException { // salDate, limit
		int limit = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select limit from account_member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next())
				limit = rs.getInt("limit");
		} finally {
			closeAll(rs, pstmt, con);
		}
		return limit;
	}

	public void updateMember(MemberVO vo) throws SQLException {
		System.err.println(vo);
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			String sql = "update account_member set password=?,name=?,limit=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getPassword());
			pstmt.setString(2, vo.getName());
			pstmt.setInt(3, vo.getLimit());

			pstmt.setString(4, vo.getId());
			/* pstmt.setString(2, vo.getName()); */
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	// 권한 부여/해제 위한 memberList(admin.jsp에서 table로 뿌려줌)
	public ArrayList<MemberVO> getAllMemberList() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		try {
			con = getConnection();
			String sql = "select id, name, authority from account_member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new MemberVO(rs.getString(1), rs.getString(2), rs.getInt(3)));
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}

	// 권한 부여 및 해제, authoNum이 1이면 -> set authority = 0
	// 0이면 -> set authority = 1
	public void updateAuthority(int authoNum, String id) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			con = getConnection();
			if (authoNum == 1)
				sql = "update account_member set authority=0 where id=?";
			else if (authoNum == 0)
				sql = "update account_member set authority=1 where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

}