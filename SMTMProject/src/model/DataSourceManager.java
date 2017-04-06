package model;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

/**
 * DBCP
 * WAS(Tomcat)에서 제공하는 dbcp(database connection pool)을 생성해
 * 사용한다
 * WAS별 제공하는 DBCP는 다양하므로
 * javax.sql.DataSource Interface 타입으로 관리한다
 * @author kosta
 *
 */

public class DataSourceManager {
	private static DataSourceManager instance = new DataSourceManager();
	private DataSource dataSource;
	//DataSource interface
	private DataSourceManager(){
		//tomvat dbcp를 생성
		BasicDataSource dbcp = new BasicDataSource();
		dbcp.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dbcp.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
		dbcp.setUsername("scott");
		dbcp.setPassword("tiger");
		dataSource = dbcp;
	}
	
	public static DataSourceManager getInstance(){
		return instance;
	}
	
	public DataSource getDataSource(){
		return dataSource;
	}
}
