package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.omg.CORBA.COMM_FAILURE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.Util;

public class SelectService {
	
	private final static Logger log = LoggerFactory.getLogger(SelectService.class);
	//DESCRIBE info;
	//수행 시간 : 000 ms 총 건수 : 000 Limited to XXX rows
	public JSONObject process(JSONObject obj,Connection conn)throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsm = null;
		List<List<String>> data = new ArrayList<List<String>>();
		List<List<String>> meta = new ArrayList<List<String>>();
		int totalRow = 0;
		int limitRow = Integer.parseInt(obj.get("limitRow").toString()); //리밋 로우만큼 로우를 가져옴
		StringBuilder message = new StringBuilder();
		JSONObject resultObj = null;
		
		try {
			String query = obj.get("query").toString();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(query);
			rsm = rs.getMetaData();
			
			long processTime = extractSelectData(rs, rsm, data, limitRow);
			message.append("수행 시간 :"+processTime+" ms ");
			
			//총건 수 추출
			if(obj.get("isTotal").toString().equals("true")){
				rs = stmt.executeQuery(query);
				rs.last();
				totalRow = rs.getRow();
				message.append("총 건수 : "+totalRow+" ");
				//rs.first();
				Util.close(rs);				
				
			}
			message.append("Limited to "+limitRow+" rows");
			
			//데이블 정보 추출
			rs = stmt.executeQuery(query);
			rsm = rs.getMetaData();
			extractTableInfo(rs, rsm, meta);
						
			resultObj = new JSONObject();
			resultObj.put("type","select");
			resultObj.put("data", data);
			resultObj.put("message",message.toString());
			resultObj.put("meta",meta);
		} catch (SQLException e) {
			log.error("SelectService 예외 : "+e.getMessage(), e);
			throw e;
		}finally {
			Util.close(conn, stmt, rs);
		}
	
		return resultObj;
	}
	
	//select 쿼리의 결과 정보를 뽑아오는 메소드
	private long extractSelectData(ResultSet rs,ResultSetMetaData rsm, List<List<String>> data,int limitRow)throws SQLException{
		//select 쿼리를 이용한 데이터 추출
		int rowCnt = 0;
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long processTime = 0;
		try {
			List<String> columnNames = new ArrayList<String>();
			for(int i = 1; i <= rsm.getColumnCount(); i++){
				columnNames.add(rsm.getColumnName(i));
			}
			data.add(columnNames);
			
			while(rs.next()){
				
				if(limitRow <= rowCnt)
					break;		
				
				List<String> column = new ArrayList<String>();
				for(int i = 1; i <= rsm.getColumnCount(); i++){
					 column.add(rs.getString(i));
				}
				data.add(column);
				rowCnt++;
					
			}
			endTime=System.currentTimeMillis();
			processTime=endTime-startTime;
		} catch (SQLException e) {
			throw e;
		}finally{
			Util.close(rs);
		}
		return processTime;		
	}
	
	
	//select 쿼리의 결과로 나온 테이블 정보를 추출한다.
	private void extractTableInfo(ResultSet rs,ResultSetMetaData rsm,List<List<String>> meta)throws SQLException{		
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("INDEX");
		columnNames.add("NAME");
		columnNames.add("TYPE");
		columnNames.add("PRECISION");
		columnNames.add("NULLABLE");
		meta.add(columnNames);
		
		try {
			for(int i=1;i<=rsm.getColumnCount();i++){
				List<String> column = new ArrayList<String>();
				column.add(String.valueOf(i));
				column.add(rsm.getColumnName(i));
				column.add(String.valueOf(rsm.getColumnTypeName(i)));
				column.add(String.valueOf(rsm.getPrecision(i)));
				column.add((rsm.isNullable(i) == 0)?"NO":"YES");
				meta.add(column);				
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			Util.close(rs);
		}		
	}
}