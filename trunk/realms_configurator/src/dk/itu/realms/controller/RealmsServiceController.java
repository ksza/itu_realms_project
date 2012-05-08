package dk.itu.realms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dk.itu.realms.model.entity.Realm;
import dk.itu.realms.model.entity.RealmList;
import dk.itu.realms.services.RealmsService;

@Controller
@RequestMapping("/realms")
public class RealmsServiceController {

	@Autowired
	private RealmsService realmsService;
	
	@RequestMapping( method=RequestMethod.GET)
	@ResponseBody
	public RealmList getRealms(@RequestParam("lat") String lat, @RequestParam("lon") String lon ) {
		List<Realm> realms = realmsService.getRealmsByLocation(lat, lon);
		return new RealmList(realms);
	}
	
	
}
