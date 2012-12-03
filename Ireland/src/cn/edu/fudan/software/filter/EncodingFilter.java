package cn.edu.fudan.software.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 编码过滤器
 * 
 * @author zyf
 * 
 */
public class EncodingFilter implements Filter {
	private FilterConfig filterConfig;
	private String encoding;

	public void destroy() {
		this.filterConfig = null;

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		encoding = filterConfig.getInitParameter("encoding");
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;

	}
}
