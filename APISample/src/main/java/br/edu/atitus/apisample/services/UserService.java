package br.edu.atitus.apisample.services;

import org.springframework.stereotype.Service;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.repositories.UserRepository;

@Service
public class UserService {
	
	private final UserRepository repository;
	
    public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}

	private static final String EMAIL_REGEX = "^[Za-z0-9+_.-]+@[Za-z0-9.-]+\\.[Za-z]{2,}$";

    public UserEntity save(UserEntity newUser) throws Exception {
        // TODO Validar regras de negócio
        if (newUser == null) 
            throw new Exception("Objeto nulo!");
        if (newUser.getName() == null || newUser.getName().isEmpty())
            throw new Exception("Nome Inválido!");
        newUser.setName(newUser.getName().trim());

        if (newUser.getEmail() == null || newUser.getEmail().isEmpty())
            throw new Exception("Email Inválido!");
        newUser.setEmail(newUser.getEmail().trim());
        
        
        if (repository.existsByEmail(newUser.getEmail()))
        	throw new Exception("Já existe usuário com este e-mail!");
        
        this.repository.save(newUser);

        if (!isValidEmail(newUser.getEmail())) {
            throw new Exception("Email Inválido!");
        }

        return newUser;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }
}
