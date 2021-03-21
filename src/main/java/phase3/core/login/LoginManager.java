package phase3.core.login;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import phase3.core.repositories.CouponsExceptions;
import phase3.core.service.AdminImpl;
import phase3.core.service.ClientService;
import phase3.core.service.CompanyImpl;
import phase3.core.service.CustomerImpl;



@Component
public class LoginManager implements ApplicationContextAware{

	private ClientService clientService;

	//@Enumerated(EnumType.STRING)
	//private ClientType clientType;
	
	private ApplicationContext ctx;

	public LoginManager() {

	}

	public ClientService logIn(String email, String password, ClientType clientType) throws CouponsExceptions {
		try {
			if (clientType.equals(ClientType.ADMINISTRATOR)) {
				clientService = ctx.getBean(AdminImpl.class);
				if (clientService.login(email, password)) {
					return clientService;
				}
			} else if (clientType.equals(ClientType.COMPANY)) {
				clientService = ctx.getBean(CompanyImpl.class);
				if (clientService.login(email, password)) {
					return clientService;
				}
			} else if (clientType.equals(ClientType.CUSTOMER)) {
				clientService = ctx.getBean(CustomerImpl.class);
				if (clientService.login(email, password)) {
					return clientService;
				}
			} else {
				return null;
			}
		} catch (CouponsExceptions e) {

			throw new CouponsExceptions("failed to login ");

		}

		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
		
	}

}
