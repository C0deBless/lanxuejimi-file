package com.tech.dal;

import java.sql.*;
public class DBUtil {
	private Connection _conn;
	private String _strDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String _strConn="jdbc:sqlserver://localhost:1433;DatabaseName=JavaTest;user=sa;password=lan;";
	private String _strComm;
	private Statement _state;
	public DBUtil() throws ClassNotFoundException, SQLException{
		this.init();
	}
	public void init() throws ClassNotFoundException, SQLException{
		Class.forName(this._strDriver);
		this._conn=DriverManager.getConnection(this._strConn);
		this._state=this._conn.createStatement();
	}
	public void setCommandString(String strComm){
		if(strComm!=null){
			this._strComm=strComm;
		}
	}
	public void executeNonQuery() throws SQLException{
		if(this._state!=null&&this._strComm!=null){
			this._state.execute(this._strComm);
		}
	}
	public ResultSet executeRSQuery() throws SQLException{
		if(this._state!=null&&this._strComm!=null){
			ResultSet rs=this._state.executeQuery(this._strComm);
			return rs;
		}
		else{
			return null;
		}
	}
	public void executeNonQuery(String strComm) throws SQLException{
		if(this._state!=null&&strComm!=null){
			this._state.execute(strComm);
		}
	}
	public ResultSet executeRSQuery(String strComm) throws SQLException{
		if(this._state!=null&&strComm!=null){
			ResultSet rs=this._state.executeQuery(strComm);
			return rs;
		}
		else{
			return null;
		}
	}
	protected void finalize(){
		try {
			this._state.close();
			this._conn.close();
			this._conn=null;
			this._state=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
