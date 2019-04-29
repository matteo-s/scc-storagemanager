package it.smartcommunitylab.storagemanager.model;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.storagemanager.SystemKeys;
import it.smartcommunitylab.storagemanager.event.ResourceEventHandler;

@Component
public class ResourceListener {
	/*
	 * Callbacks
	 */
	@PostPersist
	private void postPersist(final Resource entity) {
		service.notifyAction(entity.getUserId(), entity.getType(), entity.getId(), SystemKeys.ACTION_CREATE);
	}

	@PostUpdate
	private void postUpdate(final Resource entity) {
		service.notifyAction(entity.getUserId(), entity.getType(), entity.getId(), SystemKeys.ACTION_UPDATE);
	}

	@PreRemove
	private void preRemove(final Resource entity) {
		service.notifyAction(entity.getUserId(), entity.getType(), entity.getId(), SystemKeys.ACTION_DELETE);
	}

	/*
	 * Service
	 */
	private ResourceEventHandler service;

	@Autowired
	public ResourceListener(ResourceEventHandler rs) {
		service = rs;
	}
}
