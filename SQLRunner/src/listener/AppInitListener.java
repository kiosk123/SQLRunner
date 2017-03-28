package listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.HomeController;
import controller.SQLProcessController;
import service.DescService;
import service.EtcService;
import service.SelectService;

@WebListener
public class AppInitListener implements ServletContextListener {

	private final static Logger log = LoggerFactory
			.getLogger(AppInitListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {

		ServletContext sc = event.getServletContext();
		extractDBNameFromProperties(sc);
		
		SelectService selectService = new SelectService();
		DescService descService = new DescService();
		EtcService etcService = new EtcService();
		
		sc.setAttribute("/index.do", new HomeController());
		sc.setAttribute("/SQLProcess.do", new SQLProcessController(selectService,descService,etcService));
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	private void extractDBNameFromProperties(ServletContext sc) {
		String propsPath = sc.getRealPath("/")+File.separator+"properties"+File.separator+"db.properties";
		Properties pr = new Properties();
		FileInputStream f = null;
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
		String keys = pr.getProperty("keys");
		log.info("AppInitLister에서  key 프로퍼티 값 추출 : "+keys);
		List<String> list = createDBList(keys);
		sc.setAttribute("keys",list);
	}
	
	private List<String> createDBList(String keys){
		List<String> list = new ArrayList<String>();
		StringTokenizer stringTokenizer = new StringTokenizer(keys,",");
		while(stringTokenizer.hasMoreTokens()){
			String token = stringTokenizer.nextToken();
			log.info("token 값 추출 : "+token);
			list.add(token);
		}
		return list;
	}
}
