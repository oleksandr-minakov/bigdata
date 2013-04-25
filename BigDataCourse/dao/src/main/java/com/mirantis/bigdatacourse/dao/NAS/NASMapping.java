package com.mirantis.bigdatacourse.dao.NAS;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.FSMapping;
import org.apache.log4j.Logger;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NASMapping implements FSMapping{
	
	public static final Logger LOG = Logger.getLogger(NASMapping.class);
	private String workingDirectory;
	private int nastity;
	private final String hashType = "MD5";
	private File directory;
	
	public NASMapping(String workingDirectory, int nastity) {
		
		super();
		this.workingDirectory = workingDirectory;
		this.nastity = nastity;
		if(!new File(this.workingDirectory).exists()) {
			
			LOG.debug("Checking working directory for existance");
			directory = new File(this.workingDirectory);
			directory.mkdirs();
		}
		else
			directory = new File(this.workingDirectory);
	}
	
	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	@Override
	public String getWorkingDirectory() {
		return workingDirectory;
	}
	@Override
	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
	@Override
	public String getAbsolutePath(String id) throws DaoException {
		
		String hash = new String();
		String absPath = this.workingDirectory;
		LOG.debug("Culcucating MD5 hash ...");
		hash = getHash(id);
		
		for(int i = 0; i < this.nastity; ++i) {
			String nastity = hash.substring(i*this.nastity, (i+1)*this.nastity) + "/";
			absPath +=nastity;
		}
		LOG.debug("Forming new path");
		LOG.info("Forming new absolute path");
		return absPath;
	}
	@Override
	public String getHash(String id) throws DaoException {
		
		String hash = new String();
		MessageDigest hashAlg;
		
		try {
			hashAlg = MessageDigest.getInstance(hashType);
			hashAlg.reset();
			hashAlg.update(id.getBytes());
			byte[] byteHash = hashAlg.digest();
			for (int i=0; i < byteHash.length; i++) {
				hash += Integer.toString( ( byteHash[i] & 0xff ) + 0x100, 16).substring( 1 );
		       }
		} catch (NoSuchAlgorithmException e) {
			throw new DaoException(e);
		}
		LOG.debug("Calculation new hash by id MD5(id)");
		return hash;
	}
	@Override
	public int createDirectoryRecursively(String id) throws DaoException {
		
		LOG.debug("Creating directories recursively");
		boolean flag = new File(getAbsolutePath(id)).mkdirs();
		
		if(flag == true)
			return Integer.valueOf(id);
		else{
			return 0;
		}
	}
	@Override
	public int writeFile(String id, InputStream is) throws DaoException, IOException {
		
		LOG.debug("Mapping file to NAS...");
		int res = createDirectoryRecursively(id);
		File file = new File(getAbsolutePath(id)+ is.toString());
		
		if(res !=0) {
			
			byte[] toWrap = new byte[is.available()];
			is.read(toWrap);
			FileOutputStream fileWriter =new FileOutputStream(file.getAbsoluteFile());
			fileWriter.write(toWrap);
			fileWriter.flush();
			fileWriter.close();
			LOG.debug("Closing streams");
			return res;
		}
		else
			return 0;
	}
	@Override
	public int removeFile(String id) throws DaoException {
		
		try {
			Runtime.getRuntime().exec("rm -R " + this.workingDirectory + getHash(id).substring(0, this.nastity));
			LOG.debug("Removing file ...");
			return 0;
		} catch (IOException e) {
			throw new DaoException(e);
		}
	}
	@Override
	public InputStream readFile(String id) throws DaoException, IOException {
		
		LOG.debug("Reading file");
		String path= getAbsolutePath(id);
		File dir = new File(path);
		InputStream is;
		
		if(dir.isDirectory() == true && dir.listFiles().length == 1) {
			
			FileInputStream input = new FileInputStream(dir.listFiles()[0]);
			byte[] toWrap = new byte[input.available()];
			input.read(toWrap);
			is = new ByteArrayInputStream(toWrap);
			input.close();
			return is;
		}
		else{
			is = new ByteArrayInputStream("Exception occured. Please contact administrator".getBytes("UTF-8"));
			LOG.debug("Exception occured. on file reading");
			return is;
		}
			
	}

	@Override
	public void destroy() throws Exception {
		System.runFinalization();
		System.gc();
	}
	
}
