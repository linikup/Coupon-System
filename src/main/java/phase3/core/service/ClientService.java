package phase3.core.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import phase3.core.repositories.CouponsExceptions;

@Service
@Transactional
public interface ClientService {

	public abstract boolean login(String email, String password) throws CouponsExceptions;

}
