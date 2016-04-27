package com.mhe.rest.datacash.provider;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

import javax.ws.rs.core.Application;

//import com.mhe.rest.datacash.DataCashAbstractService;
import com.mhe.rest.datacash.client.DataCashClient;
import com.mhe.rest.datacash.exception.AppExceptionMapper;
import com.mhe.rest.datacash.exception.GenericExceptionMapper;
import com.mhe.rest.datacash.exception.RunTimeExceptionMapper;

@ApplicationPath("/tokenization")
public class DataCashHPPApplication extends Application {

	public Set<java.lang.Class<?>> getClasses() {
		Set<java.lang.Class<?>> provider = new HashSet<Class<?>>();
		provider.add(DataCashClient.class);
		provider.add(AppExceptionMapper.class);
		provider.add(GenericExceptionMapper.class);
		provider.add(RunTimeExceptionMapper.class);

		return provider;
	}
}
