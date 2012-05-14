package dk.itu.realms.controller;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dk.itu.realms.model.entity.Realm;
import dk.itu.realms.model.entity.reduced.RealmList;
import dk.itu.realms.services.RealmsService;

@Controller
@RequestMapping("/realms")
public class RealmsServiceController {

	@Autowired
	private RealmsService realmsService;
	
	@RequestMapping( method=RequestMethod.GET)
	@ResponseBody
	public RealmList getRealms(@RequestParam("lat") String lat, @RequestParam("lon") String lon, @RequestParam("userid") String userId ) {
		List<Realm> realms = realmsService.getRealmsByLocation(lat, lon, userId);
		return new RealmList(realms);
	}
	
	@RequestMapping( method=RequestMethod.GET, value="/realm")
	@ResponseBody
	public Realm getRealm(@RequestParam("realmId") long realmId) {
		return realmsService.getRealm(realmId);
	}
	
}
