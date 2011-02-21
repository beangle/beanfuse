package org.beanfuse.db.replication.wrappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.beanfuse.db.meta.TableMetadata;
import org.beanfuse.db.replication.DataWrapper;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;

public class DBFDataWrapper implements DataWrapper {

	private File dataFile;

	private DBFReader dbfReader;

	private DBFWriter dbfWriter;

	public List getData(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDataFile(File file) {
		this.dataFile = file;
		if (null != dataFile) {
			try {
				dbfReader = new DBFReader(new FileInputStream(dataFile));
				dbfWriter = new DBFWriter();
			} catch (DBFException e) {
				throw new RuntimeException(e);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void close() {
		if (null != dataFile) {
			try {
				dbfWriter.write(new FileOutputStream(dataFile));
			} catch (DBFException e) {
				throw new RuntimeException(e);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public List getData(TableMetadata tableMetaData) {
		// TODO Auto-generated method stub
		return null;
	}

	public void pushData(TableMetadata tableMetadata, List data) {
		// TODO Auto-generated method stub
		
	}

	public int count(TableMetadata table) {
		// TODO Auto-generated method stub
		return 0;
	}

}
