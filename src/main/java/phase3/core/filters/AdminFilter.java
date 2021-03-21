package phase3.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import phase3.core.sessions.Session;
import phase3.core.sessions.SessionContext;
import phase3.core.login.ClientType;

public class AdminFilter implements Filter {

	private SessionContext sessionContext;

	public AdminFilter(SessionContext sessionContext) {
		super();
		this.sessionContext = sessionContext;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("Access-Control-Max-Age", "-1");
		System.out.println("Admin filter: " + req.getMethod());

		String token = req.getHeader("token");
		if (token != null) {
			Session session = sessionContext.getSession(token);
			if ((session != null) && (session.getAttribute("clientType").equals(ClientType.ADMINISTRATOR))) {
				System.out.println("admin session good - forward the request");
				chain.doFilter(request, response);
				return;

			}
		}

		if (req.getMethod().equalsIgnoreCase("OPTIONS")) {
			System.out.println("this is preflight: " + req.getMethod());
			chain.doFilter(request, response);

		} else {
			resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
			resp.setHeader("Access-Control-Allow-Headers", "token");
			System.out.println("no session - block request and send error to client");
			resp.sendError(HttpStatus.UNAUTHORIZED.value(), " you are not logged in");
		}

	}

}
