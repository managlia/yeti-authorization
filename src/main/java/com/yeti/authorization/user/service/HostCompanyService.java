package com.yeti.authorization.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeti.model.host.HostCompany;
import com.yeti.core.repository.user.HostCompanyRepository;

@Service
public class HostCompanyService {
	
	@Autowired
	private HostCompanyRepository HostCompanyRepository;
	
	public List<HostCompany> getAllHostCompanies() {
		List<HostCompany> companies = new ArrayList<HostCompany>();
		HostCompanyRepository.findAll().forEach(companies::add);
		return companies;
	}
	
	/*
	public List<HostCompany> getAllHostCompanysByDescription(String hostCompanyDescription) {
		List<HostCompany> companies = new ArrayList<HostCompany>();
		HostCompanyRepository.findByContactDescriptionIgnoreCaseContaining(hostCompanyDescription).forEach(companies::add);
		return companies;
	}

	public List<HostCompany> getAllHostCompanysByName(String hostCompanyName) {
		List<HostCompany> companies = new ArrayList<HostCompany>();
		HostCompanyRepository.findByHostCompanyNameIgnoreCaseContaining(hostCompanyName).forEach(companies::add);
		return companies;
	}
*/	
	public HostCompany getHostCompany(Integer id) {
		return HostCompanyRepository.findOne(id);
	}
	
	public void addHostCompany(HostCompany hostCompany) {
		HostCompanyRepository.save(hostCompany);
	}

	public void updateHostCompany(Integer id, HostCompany hostCompany) {
		HostCompanyRepository.save(hostCompany);
	}

	public void deleteHostCompany(Integer id, HostCompany hostCompany) {
		HostCompanyRepository.delete(id);
	}
	
}
