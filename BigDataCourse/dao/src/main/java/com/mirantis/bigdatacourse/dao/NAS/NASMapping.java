package com.mirantis.bigdatacourse.dao.NAS;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.FSMapping;
import com.mirantis.bigdatacourse.dao.NasException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NASMapping implements FSMapping{
	
	public static final Logger LOG = Logger.getLogger(NASMapping.class);
    private File directory;

    @Value("#{properties.working_directory}")
    public String workingDirectory;

    @Value("#{properties.nesting}")
	private int nesting;

    public NASMapping() {
    }

    public NASMapping(String workingDirectory, int nesting) throws NasException {
		super();
		this.workingDirectory = workingDirectory;
		this.nesting = nesting;
		if(!(new File(this.workingDirectory).exists())) {
			LOG.debug("Checking working directory for exists.");
			directory = new File(this.workingDirectory);
            if (!directory.mkdirs()) {
                throw new NasException("Can't create directory " + this.workingDirectory);
            }
		} else {
            directory = new File(this.workingDirectory);
        }
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
	public String getAbsolutePath(String id) throws NasException {
		String hash;
		String absPath = this.workingDirectory;
		LOG.debug("Calculating MD5 hash ...");
		hash = getHash(id);
		for(int i = 0; i < this.nesting; ++i) {
			String nastity = hash.substring(i * this.nesting, (i + 1) * this.nesting) + "/";
			absPath += nastity;
		}
		LOG.debug("Forming new path");
		LOG.info("Forming new absolute path");
		return absPath;
	}

	@Override
	public String getHash(String id) throws NasException {
		String hash = "";
		MessageDigest hashAlg;
		try {
            String hashType = "MD5";
            hashAlg = MessageDigest.getInstance(hashType);
			hashAlg.reset();
			hashAlg.update(id.getBytes());
			byte[] byteHash = hashAlg.digest();
            for (byte aByteHash : byteHash) {
                hash += Integer.toString((aByteHash & 0xff) + 0x100, 16).substring(1);
            }
		} catch (NoSuchAlgorithmException e) {
			throw new NasException(e);
		}
		LOG.debug("Calculation new hash by id MD5(id)");
		return hash;
	}

	@Override
	public int createDirectoryRecursively(String id) throws DaoException {
		LOG.debug("Creating directories recursively");
		boolean isCreate = new File(getAbsolutePath(id)).mkdirs();
		if(isCreate) {
            return 0;
        } else {
			throw new NasException("Can't create directory.");
		}
	}

	@Override
	public int writeFile(String id, InputStream is) throws DaoException, IOException {
		LOG.debug("Mapping file to NAS...");
		int res = createDirectoryRecursively(id);
		File file = new File(getAbsolutePath(id) + is.toString());
        if (res == 0) {
            byte[] toWrap = new byte[is.available()];
            is.read(toWrap);
            FileOutputStream fileWriter = new FileOutputStream(file.getAbsoluteFile());
            fileWriter.write(toWrap);
            fileWriter.flush();
            fileWriter.close();
            LOG.debug("Closing streams");
            return res;
        } else {
            throw new DaoException("Can't write file.");
        }
    }

	@Override
	public int removeFile(String id) throws DaoException {
		try {
			Runtime.getRuntime().exec("rm -R " + this.workingDirectory + getHash(id).substring(0, this.nesting));
			LOG.debug("Removing file ...");
			return 0;
		} catch (IOException e) {
			throw new NasException(e);
		}
	}

	@Override
	public InputStream readFile(String id) throws DaoException, IOException {
		LOG.debug("Reading file");
		String path = getAbsolutePath(id);
		File dir = new File(path);
		InputStream is;
		if(dir.isDirectory() == true && dir.listFiles().length == 1) {
			FileInputStream input = new FileInputStream(dir.listFiles()[0]);
			byte[] toWrap = new byte[input.available()];
			input.read(toWrap);
			is = new ByteArrayInputStream(toWrap);
			input.close();
			return is;
		} else {
            LOG.debug("Exception occurred, on file reading");
			throw new DaoException("Can't read file.");
		}
	}

	@Override
	public void destroy() throws Exception {
		System.runFinalization();
		System.gc();
	}
}