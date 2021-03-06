package it.smartcommunitylab.storagemanager.consumer.log;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.smartcommunitylab.storagemanager.SystemKeys;
import it.smartcommunitylab.storagemanager.model.Consumer;
import it.smartcommunitylab.storagemanager.model.Registration;
import it.smartcommunitylab.storagemanager.model.Resource;

public class LogNoSqlConsumer extends Consumer {

	private final static Logger _log = LoggerFactory.getLogger(LogNoSqlConsumer.class);

	public static final String TYPE = SystemKeys.TYPE_NOSQL;
	public static final String ID = "logNoSqlConsumer";

	private int STATUS;

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	/*
	 * Init method - POST constructor since spring injects properties *after
	 * creation*
	 */
	@PostConstruct
	public void init() {
		_log.debug("init called");
		STATUS = SystemKeys.STATUS_READY;
	}

	@Override
	public int getStatus() {
		return STATUS;
	}

	@Override
	public void addResource(String scopeId, String userId, Resource resource) {
		_log.debug("add resource " + resource.toString());

	}

	@Override
	public void updateResource(String scopeId, String userId, Resource resource) {
		_log.debug("update resource " + resource.toString());

	}

	@Override
	public void deleteResource(String scopeId, String userId, Resource resource) {
		_log.debug("delete resource " + resource.toString());

	}

	@Override
	public void checkResource(String scopeId, String userId, Resource resource) {
		_log.debug("check resource " + resource.toString());

	}

	@Override
	public Registration getRegistration() {
		// not supported
		return null;
	}

}
