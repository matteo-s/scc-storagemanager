package it.smartcommunitylab.storagemanager.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.smartcommunitylab.storagemanager.model.Resource;
import it.smartcommunitylab.storagemanager.serializer.ResourceDeserializer;
import it.smartcommunitylab.storagemanager.serializer.ResourceSerializer;

@JsonSerialize(using = ResourceSerializer.class)
@JsonDeserialize(using = ResourceDeserializer.class)
public class ResourceDTO {

	public long id;

	public String type;
	public String provider;
	public String uri;

	public String userId;
	public String properties;

	public ResourceDTO() {
		id = 0;
		userId = "";

		type = "";
		provider = "";
		uri = "";

		properties = "{}";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public static ResourceDTO fromResource(Resource res) {
		ResourceDTO dto = new ResourceDTO();
		dto.id = res.getId();
		dto.userId = res.getUserId();

		dto.type = res.getType();
		dto.provider = res.getProvider();
		dto.uri = res.getUri();

		dto.properties = res.getProperties();
		if (dto.properties.isEmpty()) {
			dto.properties = "{}";
		}

		return dto;
	}

}
