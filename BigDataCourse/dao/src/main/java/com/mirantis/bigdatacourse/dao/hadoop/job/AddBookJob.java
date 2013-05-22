package com.mirantis.bigdatacourse.dao.hadoop.job;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.KeyGenerator;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.PathFormer;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddBookJob {

	public static final Logger LOG = Logger.getLogger(AddBookJob.class);
	private HadoopConnector hadoopConf;
	
	public AddBookJob(HadoopConnector conf) throws DaoException {
		this.hadoopConf = conf;
	}
	
	public int addBookJob(Book book) throws DaoException {
        List<String> mods = new ArrayList<String>();
        KeyGenerator idGen = new KeyGenerator();

        mods.add(String.valueOf(Thread.activeCount()));
        mods.add(Thread.currentThread().getName());
        mods.add(Thread.currentThread().toString());
        mods.add(Thread.currentThread().getState().toString());
        mods.add(Integer.toString(hadoopConf.worker));
        mods.add(String.valueOf(new Date().getTime()));

        String newID = idGen.getNewID(mods);
        book.setId(newID);

		FileSystem fs = hadoopConf.getFS();
		LOG.debug("Getting FileSystem ...");
		String destination = new PathFormer().formAddPath(book, hadoopConf.workingDirectory);
		Path path = new Path(destination);

		try {
	        FSDataOutputStream out = fs.create(path);
	        LOG.debug("creating new FS stream writer...");
	        out.write(book.getReadableText().getBytes());
	        LOG.debug("Writing ...");
	        out.close();
	        LOG.debug("Closing writer stream ...");
	        fs.setPermission(path, new FsPermission("777"));
	        LOG.debug("Setting permissions ...");
		} catch (IOException e) {
            throw new DaoException(e);
        }
        return 0;
	}
}
	

