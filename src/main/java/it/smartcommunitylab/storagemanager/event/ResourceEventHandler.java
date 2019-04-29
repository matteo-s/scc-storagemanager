package it.smartcommunitylab.storagemanager.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.storagemanager.model.ResourceEvent;

@Component
public class ResourceEventHandler {
	private final static Logger _log = LoggerFactory.getLogger(ResourceEventHandler.class);

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void notifyAction(String userId, String type, long id, String action) {

		_log.info("create message for " + type + " with payload " + action + ":" + String.valueOf(id));

		// create message
		ResourceEvent event = new ResourceEvent(this, userId, type, id, action);
		applicationEventPublisher.publishEvent(event);

	}
}
