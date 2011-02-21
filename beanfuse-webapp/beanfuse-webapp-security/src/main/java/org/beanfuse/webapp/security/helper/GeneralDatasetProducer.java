/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-8-22            Created
 *  
 ********************************************************************************/
package org.beanfuse.webapp.security.helper;

import java.util.Date;
import java.util.Map;

import org.jfree.data.general.Dataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

public class GeneralDatasetProducer implements DatasetProducer {

	private static final long serialVersionUID = 7802927454826215411L;

	private String producerId;

	private Dataset dataSet;

	public String getProducerId() {
		return producerId;
	}

	/**
	 * This producer's data is invalidated after 5 seconds. By this method the
	 * producer can influence Cewolf's caching behaviour the way it wants to.
	 */

	public boolean hasExpired(Map params, Date since) {
		return true;// (System.currentTimeMillis() - since.getTime()) > 5000;
	}

	public Object produceDataset(Map params) throws DatasetProduceException {
		return dataSet;
	}

	public GeneralDatasetProducer() {
		super();
	}

	public GeneralDatasetProducer(String producerId, Dataset dataSet) {
		this.producerId = producerId;
		this.dataSet = dataSet;
	}

}
