package com.madongfang;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.madongfang.api.ReturnApi;
import com.madongfang.entity.User;
import com.madongfang.service.UserService;

/**
 * Servlet Filter implementation class ApiFilter
 */
@Component
public class ApiFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ApiFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		httpResponse.setContentType("application/json");
		httpResponse.setCharacterEncoding("UTF-8");
		
		String uri = httpRequest.getRequestURI();
		String query = httpRequest.getQueryString();
		if (query != null)
		{
			logger.debug("ApiFilter: method=" + httpRequest.getMethod() + ", url=" + uri + "?" + query);
		}
		else
		{
			logger.debug("ApiFilter: method=" + httpRequest.getMethod() + ", url=" + uri);
		}
		
		/* 验证用户登陆 */
		boolean authenticated = false; // 是否已登录认证
		User user = null;
		String token = httpRequest.getParameter("token");
		if (token == null)
		{
			String authString = httpRequest.getHeader("Authorization");
			if (authString != null && authString.startsWith("Basic "))
			{
				token = authString.substring("Basic ".length());
			}
		}
		logger.debug("http token={}", token);
		HttpSession session = httpRequest.getSession(false);
		String apiString = uri.substring(httpRequest.getContextPath().length());
		if ("OPTIONS".equals(httpRequest.getMethod()) 
				|| apiString.equals("/api/login")) // 不需要登陆验证的命令
		{
			authenticated = true;
		}
		else // 普通用户登录
		{
			if (session != null)
			{
				user = (User)session.getAttribute("user");
				if (user != null)
				{
					request.setAttribute("user", user);
					authenticated = true;
				}
				else 
				{
					logger.info("session中无user属性");
					authenticated = false;
				}
			}
			else if (token != null)
			{
				user = userService.userLogin(token);
				if (user != null)
				{
					request.setAttribute("user", user);
					authenticated = true;
				}
				else 
				{
					logger.info("错误的token");
					authenticated = false;
				}
			}
			else 
			{
				logger.info("无登录信息");
				authenticated = false;
			}
		}
		
		if (!authenticated)
		{
			ReturnApi returnApi = new ReturnApi(-1, "未登陆，请先登陆！");
			httpResponse.setStatus(401);
			response.getWriter().write(new ObjectMapper().writeValueAsString(returnApi));
			return;
		}
		
		/* 权限等级限制 */
		if (user != null)
		{
			if (user.getLevel() > 1)
			{
				if (apiString.startsWith("/api/users") 
						&& ("DELETE".equals(httpRequest.getMethod()) || "POST".equals(httpRequest.getMethod()) || "PUT".equals(httpRequest.getMethod())))
				{
					ReturnApi returnApi = new ReturnApi(-2, "权限不足！");
					httpResponse.setStatus(403);
					response.getWriter().write(new ObjectMapper().writeValueAsString(returnApi));
					return;
				}
			}
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
}
