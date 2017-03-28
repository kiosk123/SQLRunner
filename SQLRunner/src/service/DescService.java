package service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.Util;

public class DescService {
	private final static Logger log = LoggerFactory.getLogger(DescService.class);

	public JSONObject process(JSONObject obj,Connection conn)throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		DatabaseMetaData dmd = null;
		List<List<String>> columns = new ArrayList<List<String>>();
		List<List<String>> indexes = new ArrayList<List<String>>();
		JSONObject resultObj = null;
		
		try {
			String query = obj.get("query").toString();
			String tableName = getTableName(query);
			
			//oracle은 테이블 이름을 무조건 대문자로 찾음 
			if(conn.getClass().getName().startsWith("oracle.jdbc")){
				tableName = tableName.toUpperCase();
			}
			
			//columns 추출
			dmd = conn.getMetaData();
			rs = dmd.getColumns(null,null,tableName,null);
			extractColumns(tableName, rs, columns);
			
			
			//indexes 추출
			rs = dmd.getIndexInfo(null, null,tableName, true, true);
			extractIndexes(tableName, rs, indexes);
			
			
		} catch (SQLException e) {
			log.error("DescService 예외 : "+e.getMessage(), e);
			throw e;
		}finally {
			Util.close(conn, stmt, rs);
		}
		resultObj = new JSONObject();
		resultObj.put("type","desc");
		resultObj.put("columns", columns);
		resultObj.put("indexes",indexes);
		return resultObj;
	}
	
	//테이블 이름 추출
	private String getTableName(String query)throws SQLException{
		StringTokenizer tokenizer = new StringTokenizer(query);
		String token = null;
		while(tokenizer.hasMoreTokens()){
			token = tokenizer.nextToken();
			if(token.startsWith("desc")){
				try {
					token = tokenizer.nextToken();
					log.info(token);
				} catch (NoSuchElementException e) {
					throw new SQLException("Your SQL Syntax is incorrect : "+query+";");
				}
			}
		}
		return token;
	}
	
	//컬럼 추출
	private void extractColumns(String tableName,ResultSet rs,List<List<String>> columns)throws SQLException{
		
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("COLUMN_NAME");
		columnNames.add("TYPE_NAME");
		columnNames.add("IS_NULLABLE");
		columnNames.add("COLUMN_SIZE");
		columnNames.add("ORDINAL_POSITION");
		columns.add(columnNames);
		
		try {
			if(rs.next()){
				do{
					List<String> data = new ArrayList<String>();
					data.add(rs.getString("COLUMN_NAME"));
					data.add(rs.getString("TYPE_NAME"));
					data.add(rs.getString("IS_NULLABLE"));
					data.add(rs.getString("COLUMN_SIZE"));
					data.add(rs.getString("ORDINAL_POSITION"));
					columns.add(data);
				}while(rs.next());
			}else{
				throw new SQLException("Don't search table : "+tableName+" Check your table name!");
			}
		} catch (SQLException e) {
			throw e;
		}finally {
			Util.close(rs);
		}		
	}
	
	//indexes 추출
	private void extractIndexes(String tableName,ResultSet rs,List<List<String>> indexes)throws SQLException{
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("COLUMN_NAME");
		columnNames.add("INDEX_NAME");
		columnNames.add("ORDINAL_POSITION");
		columnNames.add("ASC_OR_DESC");
		columnNames.add("CARDINALITY");
		indexes.add(columnNames);
		
		try {
			if(rs.next()){
				do{
					List<String> data = new ArrayList<String>();
					data.add(rs.getString("COLUMN_NAME"));
					data.add(rs.getString("INDEX_NAME"));
					data.add(rs.getString("ORDINAL_POSITION"));
					data.add(rs.getString("ASC_OR_DESC"));
					data.add(rs.getString("CARDINALITY"));
					indexes.add(data);
				}while(rs.next());
			}else{
				throw new SQLException("Don't search table : "+tableName+" Check your table name!");
			}
		} catch (SQLException e) {
			throw e;
		}finally {
			Util.close(rs);
		}		
	}
}
