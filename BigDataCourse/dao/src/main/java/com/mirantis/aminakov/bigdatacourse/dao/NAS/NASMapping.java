package com.mirantis.aminakov.bigdatacourse.dao.NAS;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.FSMapping;

public class NASMapping implements FSMapping{
	
	
	private String workingDirectory;
	private int nastity;
	private final String hashType = "MD5";
	private File directory;
	
	public NASMapping(String workingDirectory, int nastity) {
		
		super();
		this.workingDirectory = workingDirectory;
		this.nastity = nastity;
		if(!new File(this.workingDirectory).exists()){
			
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
	public String getAbsolutePath(int id) throws DaoException{
		
		String hash = new String();
		String absPath = this.workingDirectory;
		hash = getHash(id);
		
		for(int i = 0; i < this.nastity; ++i){
			String nastity = hash.substring(i*this.nastity, (i+1)*this.nastity) + "/";
			absPath +=nastity;
		}
		
		return absPath;
	}
	@Override
	public String getHash(int id) throws DaoException{
		
		String hash = new String();
		MessageDigest hashAlg;
		
		try {
			hashAlg = MessageDigest.getInstance(hashType);
			hashAlg.reset();
			hashAlg.update(String.valueOf(id).getBytes());
			byte[] byteHash = hashAlg.digest();
			for (int i=0; i < byteHash.length; i++) {
				hash += Integer.toString( ( byteHash[i] & 0xff ) + 0x100, 16).substring( 1 );
		       }
		} catch (NoSuchAlgorithmException e) {
			throw new DaoException(e);
		}
		
		return hash;
	}
	@Override
	public int createDirectoryRecursively(int id) throws DaoException{
		
		boolean flag = new File(getAbsolutePath(id)).mkdirs();
		
		if(flag == true)
			return id;
		else{
			return 0;
		}
	}
	@Override
	public int writeFile(int id, InputStream is) throws DaoException, IOException{
		
		int res = createDirectoryRecursively(id);
		File file = new File(getAbsolutePath(id)+ is.toString());
		
		if(res !=0){
			
			byte[] toWrap = new byte[is.available()];
			is.read(toWrap);
			FileOutputStream fileWriter =new FileOutputStream(file.getAbsoluteFile());
			fileWriter.write(toWrap);
			fileWriter.flush();
			fileWriter.close();
			return res;
		}
		else
			return 0;
	}
	@Override
	public int removeFile(int id) throws DaoException{
		
		try {
			Runtime.getRuntime().exec("rm -R " + this.workingDirectory + getHash(id).substring(0, this.nastity));
			return id;
		} catch (IOException e) {
			throw new DaoException(e);
		}
	}
	@Override
	public InputStream readFile(int id) throws DaoException, IOException{
		
		String path= getAbsolutePath(id);
		File dir = new File(path);
		InputStream is;
		
		if(dir.isDirectory() == true && dir.listFiles().length == 1){
			
			FileInputStream input = new FileInputStream(dir.listFiles()[0]);
			byte[] toWrap = new byte[input.available()];
			input.read(toWrap);
			is = new ByteArrayInputStream(toWrap);
			input.close();
			return is;
		}
		else{
			is = new ByteArrayInputStream("Exception occured. Please contact administrator".getBytes("UTF-8"));
			return is;
		}
			
	}

	@Override
	public void destroy() throws Exception {
	}
	
}
