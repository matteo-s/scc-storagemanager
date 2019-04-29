package it.smartcommunitylab.storagemanager.model;

import org.json.JSONObject;

public abstract class Provider {

	/*
	 * Provider
	 */
	public abstract String getId();

	public abstract String getType();

	public abstract int getStatus();

	/*
	 * Resources
	 */
	public abstract Resource createResource(String userId, JSONObject properties);

	public abstract void updateResource(Resource resource);

	public abstract void deleteResource(Resource resource);

	public abstract void checkResource(Resource resource);

//	/*
//	 * Properties
//	 */
//	public abstract Set<String> listProperties();

}
