package com.ppm.integration.agilesdk.connector.jira.test;

import java.util.List;

import com.ppm.integration.agilesdk.ValueSet;
import com.ppm.integration.agilesdk.ui.DynamicDropdown;

public class Test {

}

class DDL extends DynamicDropdown {

	public DDL(String name, String labelKey, boolean isRequired) {
		super(name, labelKey, isRequired);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getDependencies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Option> getDynamicalOptions(ValueSet arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
