package it.smartcommunitylab.storagemanager.model;

import org.json.JSONObject;

import it.smartcommunitylab.storagemanager.common.NoSuchConsumerException;

public interface ConsumerBuilder {

	public Consumer build() throws NoSuchConsumerException;

	public Consumer build(JSONObject properties) throws NoSuchConsumerException;

	public Consumer build(Registration reg) throws NoSuchConsumerException;

}
