package it.smartcommunitylab.storagemanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.json.JSONObject;

@Entity
public class Registration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String type;

	private String consumer;

	private String userId;

	private String properties;

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

	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
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

	@Override
	public String toString() {
		return "Registration [id=" + id + ", type=" + type + ", consumer=" + consumer + ", userId=" + userId + "]";
	}

	@Transient
	private JSONObject json;

	public JSONObject getPropertiesMap() {
		if (this.json == null) {
			json = new JSONObject(properties);
		}
		return json;
	}

	public void setPropertiesMap(JSONObject json) {
		this.json = json;
		sync();
	}

	@PrePersist
	@PreUpdate
	private void sync() {
		if (json != null) {
			properties = json.toString();
		}
	}
}
