package dk.itu.realms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dk.itu.realms.model.entity.PhoneUser;
import dk.itu.realms.services.PhoneUserService;

@Controller
@RequestMapping("/phoneuser")
public class PhoneUserServiceController {

	@Autowired
	private PhoneUserService puService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PhoneUser getUser(@RequestParam("userid") long userId) {
		PhoneUser user = puService.getUser(userId);
		return user;
	}

	@RequestMapping(method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String createUser(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("fullname") String fullname) {
		PhoneUser user = puService.createUser(username, password, fullname);
		if(user != null ) {
			return "redirect:/services/phoneuser?id=" + user.getId() ;
		} else {
			//User already exists
			return "";
		}
	}
}
