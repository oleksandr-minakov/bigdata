package com.mirantis.aminakov.bigdatacourse.dao.cassandra;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {

	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) {

		BasicConfigurator.configure();
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/DAOConfig.xml");
		
		Constants cts = (Constants) ctx.getBean("constants");
		System.out.println(cts.getCurrentClstr().getName());
		System.out.println(cts.getKeyspace().getKeyspaceName());
		System.out.println(cts.CF_NAME);
		DaoApp dao = (DaoApp) ctx.getBean("cassandra");
		cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
	}

}
