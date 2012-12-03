package cn.edu.fudan.software.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于安全性判断，防止未登录用户访问受限制资源
 * 
 * @author zyf
 * 
 */
public class SecurityFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 若已登录，或请求登录，则交给下一个过滤器处理
		if (((HttpServletRequest) request).getSession().getAttribute(
				"loginUser") != null
				&& ((HttpServletRequest) request).getSession().getAttribute(
						"role").equals("admin")) {
			chain.doFilter(request, response);
		}

		// 若用户未登录且请求资源非登录页面，则返回登录页面
		else {
			// System.out.println("path:");
			// if
			// (((HttpServletRequest)request).getRequestURI().indexOf("/jsp/")!=-1)
			// {
			// ((HttpServletRequest)request).getSession().setAttribute("errorMessage",
			// "无此权限，请先登录！");
			((HttpServletResponse) response).sendRedirect("login.jsp");
			// }
			// else
			// {
			// ((HttpServletRequest)request).getSession().setAttribute("errorMessage",
			// "");
			// ((HttpServletResponse)response).sendRedirect("jsp/login.jsp");
			// }

		}
	}
}
