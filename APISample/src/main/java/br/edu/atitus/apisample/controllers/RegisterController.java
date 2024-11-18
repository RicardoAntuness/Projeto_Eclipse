package br.edu.atitus.apisample.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.apisample.dtos.RegisterDTO;
import br.edu.atitus.apisample.entities.RegisterEntity;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.services.RegisterService;
import br.edu.atitus.apisample.services.UserService;


@RestController
@RequestMapping("/registers")
public class RegisterController {

	private final RegisterService service;
	private final UserService userService;
	
	public RegisterController(RegisterService service, UserService userService) {
		super();
		this.service = service;
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<RegisterEntity> createRegister(RegisterDTO registerDTO) throws Exception{
		RegisterEntity newRegister= new RegisterEntity();
		BeanUtils.copyProperties(registerDTO, newRegister);
		
	UserEntity user = ((UserService) userService).findAll().get(0);
	newRegister.setUser(user);
	
	service.save(newRegister);
	
	return ResponseEntity.status(HttpStatus.CREATED).body(newRegister);
	}
	
	@GetMapping
	public ResponseEntity<List<RegisterEntity>> getRegisters() throws Exception{
		var registers = service.findAll();
		
		return ResponseEntity.ok(registers);
	}
}
