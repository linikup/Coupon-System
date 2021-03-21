package phase3.core.controller;


import org.springframework.http.ResponseEntity;
import phase3.core.repositories.CouponsExceptions;


public abstract class ClientController {
	
	
	
	public abstract ResponseEntity<?> login(String name, String password) throws CouponsExceptions;
	
	public abstract ResponseEntity<?> logout(String token);

}
