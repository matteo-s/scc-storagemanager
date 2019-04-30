package it.smartcommunitylab.storagemanager.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.storagemanager.SystemKeys;
import it.smartcommunitylab.storagemanager.common.NoSuchProviderException;
import it.smartcommunitylab.storagemanager.common.NoSuchResourceException;
import it.smartcommunitylab.storagemanager.model.Provider;
import it.smartcommunitylab.storagemanager.model.Resource;
import it.smartcommunitylab.storagemanager.model.ResourceEvent;
import it.smartcommunitylab.storagemanager.repository.ResourceRepository;

@Component
public class ResourceLocalService {
	private final static Logger _log = LoggerFactory.getLogger(ResourceLocalService.class);

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private ProviderLocalService providerLocalService;

	/*
	 * Data
	 */
	public Resource create(String userId, String type, String providerId, Map<String, Serializable> properties)
			throws NoSuchProviderException {
		_log.info("create " + type + " resource with " + String.valueOf(providerId) + " by user " + userId);

		// call provider to require creation
		Provider provider = providerLocalService.getProvider(providerId);
		// check type match
		if (!provider.getType().equals(type)) {
			throw new NoSuchProviderException();
		}
		// sync call - should validate properties
		Resource res = provider.createResource(userId, properties);

		// update fields
		res.setUserId(userId);

		// persist resource
		return resourceRepository.saveAndFlush(res);

	}

	public Resource update(long id, Map<String, Serializable> properties)
			throws NoSuchResourceException, NoSuchProviderException {
		_log.info("update resource " + String.valueOf(id));

		Optional<Resource> p = resourceRepository.findById(id);

		if (!p.isPresent()) {
			throw new NoSuchResourceException();
		}

		Resource res = p.get();
		// update fields
		res.setPropertiesMap(properties);

		// call provider to require update
		Provider provider = providerLocalService.getProvider(res.getProvider());
		// sync call - should validate properties
		provider.updateResource(res);

		return resourceRepository.save(res);

	}

	public void delete(long id) throws NoSuchResourceException, NoSuchProviderException {
		_log.info("delete resource " + String.valueOf(id));

		Optional<Resource> p = resourceRepository.findById(id);

		if (!p.isPresent()) {
			throw new NoSuchResourceException();
		}

		Resource res = p.get();

		// call provider to require removal
		Provider provider = providerLocalService.getProvider(res.getProvider());
		// sync call
		provider.deleteResource(res);

		// remove from DB with callback to consumers
		resourceRepository.deleteById(id);
	}

	public Resource get(long id) throws NoSuchResourceException {
		Optional<Resource> p = resourceRepository.findById(id);

		if (!p.isPresent()) {
			throw new NoSuchResourceException();
		}

		return p.get();
	}

	/*
	 * Count
	 */
	public long count() {
		return resourceRepository.count();
	}

	public long countByType(String type) {
		return resourceRepository.countByType(type);
	}

	public long countByProvider(String provider) {
		return resourceRepository.countByProvider(provider);
	}

	public long countByUserId(String userId) {
		return resourceRepository.countByUserId(userId);
	}

	public long countByTypeAndUserId(String type, String userId) {
		return resourceRepository.countByTypeAndUserId(type, userId);
	}

	public long countByProviderAndUserId(String provider, String userId) {
		return resourceRepository.countByProviderAndUserId(provider, userId);
	}
	/*
	 * List
	 */

	public List<Resource> list() {
		return resourceRepository.findAll();
	}

	public List<Resource> list(Pageable pageable) {
		Page<Resource> result = resourceRepository.findAll(pageable);
		return result.getContent();
	}

	public List<Resource> listByType(String type) {
		return resourceRepository.findByType(type);
	}

	public List<Resource> listByProvider(String provider) {
		return resourceRepository.findByProvider(provider);
	}

	public List<Resource> listByUserId(String userId) {
		return resourceRepository.findByUserId(userId);
	}

	public List<Resource> listByUserId(String userId, Pageable pageable) {
		Page<Resource> result = resourceRepository.findByUserId(userId, pageable);
		return result.getContent();
	}

	public List<Resource> listByTypeAndUserId(String type, String userId) {
		return resourceRepository.findByTypeAndUserId(type, userId);
	}

	public List<Resource> listByTypeAndUserId(String type, String userId, Pageable pageable) {
		Page<Resource> result = resourceRepository.findByTypeAndUserId(type, userId, pageable);
		return result.getContent();
	}

	/*
	 * Check
	 */
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void check(long id) throws NoSuchResourceException, NoSuchProviderException {
		Optional<Resource> p = resourceRepository.findById(id);

		if (!p.isPresent()) {
			throw new NoSuchResourceException();
		}

		Resource res = p.get();

		// call provider to require check
		Provider provider = providerLocalService.getProvider(res.getProvider());
		// sync call
		provider.checkResource(res);

		// notify all consumers via events
		ResourceEvent event = new ResourceEvent(this, res.getUserId(), res.getType(), id, SystemKeys.ACTION_CHECK);
		applicationEventPublisher.publishEvent(event);
	}

}
