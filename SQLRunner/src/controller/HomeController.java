package controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* /index.do */
public class HomeController implements Controller{

	private Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServletContext context = request.getServletContext();
		List<String> keysList = (List<String>)context.getAttribute("keys");
		request.setAttribute("keys", keysList);
		return "/WEB-INF/views/sqlrun.jsp";
	}
}
