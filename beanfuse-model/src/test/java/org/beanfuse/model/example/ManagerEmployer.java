package org.beanfuse.model.example;

import java.util.Map;

import org.beanfuse.model.pojo.LongIdObject;

public class ManagerEmployer extends LongIdObject implements Employer {

	Name name;

	ContractInfo contractInfo;

	Map skills;

	public Map getSkills() {
		return skills;
	}

	public void setSkills(Map skills) {
		this.skills = skills;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public ContractInfo getContractInfo() {
		return contractInfo;
	}

	public void setContractInfo(ContractInfo contractInfo) {
		this.contractInfo = contractInfo;
	}

}
