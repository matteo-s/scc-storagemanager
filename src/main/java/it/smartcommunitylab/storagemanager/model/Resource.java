package it.smartcommunitylab.storagemanager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.json.JSONObject;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@EntityListeners({ AuditingEntityListener.class, ResourceListener.class })
public class Resource {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String type;
	private String provider;
	private String uri;

	private String userId;
	private String properties;

	/*
	 * Audit
	 */
	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	@Temporal(TIMESTAMP)
	private Date createdDate;

	@Column(name = "modified_date")
	@LastModifiedDate
	@Temporal(TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "created_by")
	@CreatedBy
	protected String createdBy;

	@Column(name = "modified_by")
	@LastModifiedBy
	protected String lastModifiedBy;

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", type=" + type + ", provider=" + provider + ", userId=" + userId
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy + "]";
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
