package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.Util;

public class EtcService {
	private final static Logger log = LoggerFactory.getLogger(EtcService.class);

	public JSONObject process(JSONObject obj,Connection conn)throws Exception{
		
		StringBuilder message = new StringBuilder();
		JSONObject resultObj = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			long startTime=System.currentTimeMillis();
			String query = obj.get("query").toString();
			stmt=conn.createStatement();
			int affected = stmt.executeUpdate(query);
			long endTime = System.currentTimeMillis();
			long processTime = endTime-startTime;
			
			message.append("수행시간 : ");
			message.append(processTime+" ");
			message.append("수행 건수 : ");
			message.append(affected);
			
			resultObj = new JSONObject();
			resultObj.put("type","etc");
			resultObj.put("message",message.toString());
			
		}catch (SQLException e) {
			log.error("EtcService 예외 : "+e.getMessage(), e);
			throw e;
		}finally{
			Util.close(conn, stmt, rs);
		}
		return resultObj;
	}
}
