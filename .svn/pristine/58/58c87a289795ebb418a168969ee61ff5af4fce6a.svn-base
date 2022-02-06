package co.id.sofcograha.base.authentication.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;

import co.id.sofcograha.base.constants.BaseConstants;

//import id.co.sofcograha.gajiidapi.base.constants.BaseConstants;
//import id.co.sofcograha.gajiidapi.base.multitenancy.MultiTenancyConstants;

public class CorsFilter implements Filter {

	public static final List<String> allowedOrigins = Arrays.asList(
			"http://localhost:4200", 
			"http://localhost:8080",
			"http://172.168.23.8:8080",
			"http://172.168.23.8:4200",
			"http://128.64.32.8:8080",
			"http://128.64.32.8:4200",
			"http://128.64.32.18:8080",
			"http://128.64.32.18:4200",
			"http://128.64.32.243:8080",
			"http://128.64.32.243:4200"); 
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, " 
        + BaseConstants.AUTHORIZATION_HEADER + ", " + BaseConstants.MENU_CODE);
//		response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, " 
//				+ MultiTenancyConstants.KEY_TENANTID_SESSION + ", " + BaseConstants.AUTHORIZATION_HEADER);

		if (!isRequestWithMethodOption(req)) chain.doFilter(req, res);
	}

	@Override
	public void destroy() {}

	private boolean isRequestWithMethodOption(ServletRequest req) {
		HttpServletRequest sreq = (HttpServletRequest) req;
		return sreq.getMethod().toString().equalsIgnoreCase(HttpMethod.OPTIONS.name());
	}
}
