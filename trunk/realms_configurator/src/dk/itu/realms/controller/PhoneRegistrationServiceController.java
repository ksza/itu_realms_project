package dk.itu.realms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dk.itu.realms.model.entity.PhoneUser;
import dk.itu.realms.services.PhoneRegistrationService;

@Controller
@RequestMapping("/phoneregister")
public class PhoneRegistrationServiceController {
	
	@Autowired
	private PhoneRegistrationService puService;
	
	@RequestMapping( method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public PhoneUser createUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("fullname") String fullname ) {
		return puService.createUser(username, password, fullname);
	}
}
