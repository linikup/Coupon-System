package phase3.core.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phase3.core.login.ClientType;
import phase3.core.repositories.CouponsExceptions;
import phase3.core.service.AdminImpl;
import phase3.core.service.ClientService;
import phase3.core.service.CompanyImpl;
import phase3.core.service.CustomerImpl;
import phase3.core.sessions.Session;
import phase3.core.sessions.SessionContext;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController implements ApplicationContextAware {

	private ClientService clientServ;

	private ApplicationContext ctx;

	@Autowired
	private SessionContext sessionContext;


	@PostMapping("/login/{email}/{password}/{clientType}")
	public ResponseEntity<?> logIn(@PathVariable String email, @PathVariable String password,
			@PathVariable ClientType clientType) {
		try {
			if (clientType.equals(ClientType.ADMINISTRATOR)) {
				clientServ = ctx.getBean(AdminImpl.class);
				if (clientServ.login(email, password)) {
					Session session = sessionContext.createSession();
					session.setAttribute("userName", email);
					session.setAttribute("clientType", clientType);
					return ResponseEntity.status(HttpStatus.OK).body(session.token);
				}

			} else if (clientType.equals(ClientType.COMPANY)) {
				clientServ = ctx.getBean(CompanyImpl.class);
				if (clientServ.login(email, password)) {
					Session session = sessionContext.createSession();
					session.setAttribute("userName", email);
					session.setAttribute("clientType", clientType);
					return ResponseEntity.status(HttpStatus.OK).body(session.token);
				}

			} else if (clientType.equals(ClientType.CUSTOMER)) {
				clientServ = ctx.getBean(CustomerImpl.class);
				if (clientServ.login(email, password)) {
					Session session = sessionContext.createSession();
					session.setAttribute("userName", email);
					session.setAttribute("clientType", clientType);
					return ResponseEntity.status(HttpStatus.OK).body(session.token);
				}
			}
		} catch (CouponsExceptions e) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed!!!!!!!!!!!!!!!!!!!!");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;

	}

}
