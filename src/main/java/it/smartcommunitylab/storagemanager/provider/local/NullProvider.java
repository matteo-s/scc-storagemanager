package it.smartcommunitylab.storagemanager.provider.local;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.storagemanager.SystemKeys;
import it.smartcommunitylab.storagemanager.model.Provider;
import it.smartcommunitylab.storagemanager.model.Resource;
import it.smartcommunitylab.storagemanager.util.SqlUtil;

@Component
public class NullProvider extends Provider {

	private final static Logger _log = LoggerFactory.getLogger(NullProvider.class);

	public static final String TYPE = SystemKeys.TYPE_SQL;
	public static final String ID = "nullProvider";

	private int STATUS;

	@Value("${providers.null.enable}")
	private boolean enabled;

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
		_log.info("enabled " + String.valueOf(enabled));

		if (enabled) {
			STATUS = SystemKeys.STATUS_READY;
		} else {
			STATUS = SystemKeys.STATUS_DISABLED;
		}

		_log.info("init status " + String.valueOf(STATUS));
	}

	@Override
	public int getStatus() {
		return STATUS;
	}

	@Override
	public Resource createResource(String userId, JSONObject properties) {
		Resource res = new Resource();
		res.setType(TYPE);
		res.setProvider(ID);

		// generate uri
		String uri = SqlUtil.encodeURI("null", "host:981", "dbase", "USER", "PASS");
		res.setUri(uri);

		return res;
	}

	@Override
	public void updateResource(Resource resource) {
		// nothing to do

	}

	@Override
	public void deleteResource(Resource resource) {
		// nothing to do

	}

	@Override
	public void checkResource(Resource resource) {
		// nothing to do

	}

}
