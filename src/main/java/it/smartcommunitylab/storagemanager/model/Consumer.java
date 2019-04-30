package it.smartcommunitylab.storagemanager.model;

public abstract class Consumer {

	/*
	 * Consumer
	 */
	public abstract String getId();

	public abstract String getType();

	public abstract int getStatus();

	public abstract Registration getRegistration();

	/*
	 * Resources
	 */
	public abstract void addResource(String userId, Resource resource);

	public abstract void checkResource(String userId, Resource resource);

	public abstract void updateResource(String userId, Resource resource);

	public abstract void deleteResource(String userId, Resource resource);

}