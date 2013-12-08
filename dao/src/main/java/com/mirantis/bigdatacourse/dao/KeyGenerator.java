package com.mirantis.bigdatacourse.dao;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class KeyGenerator {

	public static final Logger LOG = Logger.getLogger(KeyGenerator.class);
	
	public String getNewID(List<String> modifiers) throws DaoException {
		
		Assert.notEmpty(modifiers);
		modifiers.add(KeyGenerator.class.toString());
		String ID = "";
		
		for(String mod:modifiers)
			ID +=mod;
		
		Assert.notNull(ID);
		return getHash(ID);
	}
	
	public String getHash(String id) throws DaoException {
		
		String hash = "";
		MessageDigest hashAlg;
		
		try {
			
			hashAlg = MessageDigest.getInstance("MD5");
			hashAlg.reset();
			hashAlg.update(id.getBytes());
			byte[] byteHash = hashAlg.digest();
            for (byte aByteHash : byteHash) {
                hash += Integer.toString((aByteHash & 0xff) + 0x100, 16).substring(1);
            }
		} catch (NoSuchAlgorithmException e) {
			throw new DaoException(e);
		}
		LOG.debug("Calculation new hash by id MD5(id)");
		return hash;
	}
}
