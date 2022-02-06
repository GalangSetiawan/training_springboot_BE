package co.id.sofcograha.base.authentication.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.id.sofcograha.base.authentication.anonymous.AnonymousAuthentication;
import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.ApiResponse;
import co.id.sofcograha.base.utils.Message;

public class AnonymousAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
//	private List<AntPathRequestMatcher> blockedPath;
	
	public AnonymousAuthenticationFilter() {
//		super(new AntPathRequestMatcher(BaseConstants.PUBLIC + "/**"));
		super(new AntPathRequestMatcher("/**"));
		
//		this.blockedPath = new ArrayList<>();
//		this.blockedPath.add(new AntPathRequestMatcher(BaseUriConstants.NO_AUTH_USER));
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
//		for (AntPathRequestMatcher matcher : this.blockedPath) {
//			if (matcher.matches(request)) {
//				throw new TokenAuthenticationException("Blocked URL Path!");
//			}
//		}
		
//		String tenantId = request.getHeader(MultiTenancyConstants.KEY_TENANTID_SESSION);
//		TenantContext.setCurrentTenant(tenantId);
//		TenantContext.setDefault();
		
		Authentication auth = new AnonymousAuthentication();
		SecurityContextHolder.getContext().setAuthentication(auth);
		return auth;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		if (authResult != null) SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");

		writeErrorResponse(response, ApiResponse.error(Message.create().setCode(BaseConstants.BLOCKED_PATH_ERROR_CODE).setDesc(failed.getMessage())));
	}

	private void writeErrorResponse(HttpServletResponse response, ApiResponse responseObj) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		response.getWriter().write(mapper.writeValueAsString(responseObj));
		response.getWriter().close();
	}
}
