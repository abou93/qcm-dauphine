package fr.dauphine.qcm.component.service.impl;

import static org.apache.commons.codec.digest.DigestUtils.shaHex;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.dauphine.qcm.component.repository.IUserRepository;
import fr.dauphine.qcm.component.service.IUserService;
import fr.dauphine.qcm.exception.FunctionalException;
import fr.dauphine.qcm.model.Result;
import fr.dauphine.qcm.model.User;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public User checkCredentials(User user) throws FunctionalException {
		User databaseUser = userRepository.loadByLogin(user.getLogin());
		String passwordHash = shaHex(user.getPassword());

		if (databaseUser == null
				|| !databaseUser.getPassword().equals(passwordHash)) {
			throw new FunctionalException("wrong credentials");
		}

		return databaseUser;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public User createAccount(User user) throws FunctionalException {
		User databaseUser = userRepository.loadByLogin(user.getLogin());
		String passwordHash = shaHex(user.getPassword());

		if (databaseUser != null) {
			throw new FunctionalException("login already registered");
		}

		user.setPassword(passwordHash);
		userRepository.save(user);
		
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public User getById(Long id) {
		User user = userRepository.load(id);
		
		for (Result result : user.getResults()) {
			result.getQuestionnaire().getQuestions().size(); // Lazy
		}
		
		// Les meilleurs scores en premier
		Collections.sort(user.getResults());
		
		return user;
	}
}
