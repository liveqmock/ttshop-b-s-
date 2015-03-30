package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class loginFilter extends HttpServlet implements Filter {

	
	@Override
	public void destroy() {
	}

	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		HttpSession session = httprequest.getSession();
		String contextpath = httprequest.getContextPath();
		String url = httprequest.getServletPath();
		//System.out.println(url);
		//System.out.println(url.startsWith("/user/login"));
		//System.out.println(url.indexOf("/login.action"));
		if(url.equals("")){
			url+="/";
		}
		if(url.startsWith("/")
				&&!url.startsWith("/user/login")
				&&!url.startsWith("/user/register")
				&&!url.startsWith("/user/checkuserid")
				&&!url.startsWith("/user/logout")
				&&!url.startsWith("/weixin/")
				&&!url.startsWith("/upload/")
				&&!url.startsWith("/webshop/")
				&&!url.startsWith("/user/chechLogin")
				){ //只要不是login.action，都要检测session
			if(session==null || session.getAttribute("user")==null){
				httpresponse.sendRedirect(contextpath+"/timeout.jsp");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig arg0) throws ServletException {
	}


	

}
