package com.yeti.authorization.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yeti.model.host.HostCompany;
import com.yeti.authorization.user.service.HostCompanyService;

@RestController
public class HostCompanyController {

	@Autowired
	private HostCompanyService hostService;
	
	@RequestMapping(method=RequestMethod.GET, value="/HostCompanies")
	public List<HostCompany> getAllHostCompanies() {
		return hostService.getAllHostCompanies();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/HostCompanies/{id}")
	public HostCompany getHostCompany(@PathVariable Integer id) {
		return hostService.getHostCompany(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/HostCompanies")
	public void addHostCompany(@RequestBody HostCompany hostCompany) {
		hostService.addHostCompany(hostCompany);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/HostCompanies/{id}")
	public void updateHostCompany(@RequestBody HostCompany hostCompany, @PathVariable Integer id) {
		hostService.updateHostCompany(id, hostCompany);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/HostCompanies/{id}")
	public void deleteHostCompany(@RequestBody HostCompany hostCompany, @PathVariable Integer id) {
		hostService.deleteHostCompany(id, hostCompany);
	}
	
	
}








