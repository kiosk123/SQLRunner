package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.DescService;
import service.EtcService;
import service.SelectService;

/* /SQLProcess.do */
public class SQLProcessController implements Controller {

	private final static Logger log = LoggerFactory.getLogger(SQLProcessController.class);
	private SelectService selectService;
	private DescService descService;
	private EtcService etcService;
	
	public SQLProcessController(SelectService selectService,DescService descService, EtcService etcService) {
		this.selectService = selectService;
		this.descService = descService;
		this.etcService = etcService;
	}
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String jsonString = getJsonString(request.getReader());
		log.info(jsonString);
		PrintWriter out = response.getWriter();
		JSONObject obj=(JSONObject)parseJSON(jsonString);		
		Connection conn = getConnection(request.getServletContext(),obj.get("key").toString());
		String query = obj.get("query").toString();
		
		try {
			if(query.startsWith("select")){
				obj = selectService.process(obj, conn);				
			}else if(query.startsWith("desc")){
				obj = descService.process(obj, conn);				
			}else{
				obj = etcService.process(obj, conn);				
			}			
			out.print(obj.toJSONString());
		} catch (Exception e) {
			log.error("SQLProcessController 예외 : "+e.getMessage(), e);
			StringBuilder builder = new StringBuilder();
			builder.append(e.getMessage()+"\n");
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			builder.append(errors.toString());
			response.setStatus(400);
			out.print(builder.toString());
		}	
		return null;
	}

	// JSONString 추출
	private String getJsonString(BufferedReader reader) {
		String jsonString = null;
		String line = null;
		try {
			BufferedReader buffer = reader;
			if ((line = buffer.readLine()) != null) {
				jsonString = line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonString;
	}

	// 커넥션 가져옴
	private Connection getConnection(ServletContext sc, String key){
		String propsPath = sc.getRealPath("/") + File.separator + "properties"+ File.separator + "db.properties";
		Properties pr = new Properties();
		FileInputStream f = null;
		Connection conn = null;
		try {
			// db.properties파일의 내용을 읽어옴
			f = new FileInputStream(propsPath);
			// db.properties의 내용을 Properties 객체 pr에 저장
			pr.load(f);
		} catch (IOException e) {
			log.error("db.properties 처리 예외", e);
		} finally {
			if (f != null)try {f.close();} catch (IOException ex){}
		}
		
		
		try {
			Class.forName(pr.getProperty(key + ".driverClassName"));
		} catch (ClassNotFoundException e) {
			log.error("드라이버 로드중 예외 발생", e);
		}
		
		
		try {
			String url = pr.getProperty(key + ".url");
			String user = pr.getProperty(key + ".username");
			String pass = pr.getProperty(key + ".password");
			log.info("connect : " + url + "  " + user + "  " + pass);
			
			conn= DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			log.error("커넥션 생성도중 예외 발생", e);
		}
		return conn;
	}

	//JSONString을 parse한다
	public Object parseJSON(String jsonString) {
		Object rt = null;
		JSONParser parser = new JSONParser();
		try {
			rt = parser.parse(jsonString);
		} catch (ParseException e) {
			log.error("Parsing ERROR : " +e.getMessage(),e);
			e.printStackTrace();
		}
		return rt;
	}
}
