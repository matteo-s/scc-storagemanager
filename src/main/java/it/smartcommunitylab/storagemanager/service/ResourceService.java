package it.smartcommunitylab.storagemanager.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.storagemanager.SystemKeys;
import it.smartcommunitylab.storagemanager.common.NoSuchProviderException;
import it.smartcommunitylab.storagemanager.common.NoSuchResourceException;
import it.smartcommunitylab.storagemanager.model.Resource;

@Component
public class ResourceService {

	private final static Logger _log = LoggerFactory.getLogger(ResourceService.class);

	@Autowired
	private ResourceLocalService resourceLocalService;

	/*
	 * Data
	 */
	public Resource create(String userId, String type, String providerId, Map<String, Serializable> properties)
			throws NoSuchProviderException {
		_log.info("create resource with " + String.valueOf(providerId) + " by user " + userId);

		// TODO check auth
		//
		// call local service
		return resourceLocalService.create(userId, type, providerId, properties);

	}

	public Resource update(String userId, long id, Map<String, Serializable> properties)
			throws NoSuchResourceException, NoSuchProviderException {
		_log.info("update resource " + String.valueOf(id) + " by user " + userId);

		// TODO check auth
		//
		// call local service
		return resourceLocalService.update(id, properties);

	}

	public void delete(String userId, long id) throws NoSuchResourceException, NoSuchProviderException {
		_log.info("delete resource " + String.valueOf(id) + " by user " + userId);

		// TODO check auth
		//
		// call local service
		resourceLocalService.delete(id);
	}

	public Resource get(String userId, long id) throws NoSuchResourceException {
		_log.info("get resource " + String.valueOf(id) + " by user " + userId);

		// TODO check auth
		//
		// call local service
		return resourceLocalService.get(id);
	}

	/*
	 * Count
	 */

	public long count(String userId) {
		// TODO check auth
		//
		// call local service
		return resourceLocalService.count();
	}

	public long countByType(String userId, String type) {
		// TODO check auth
		//
		// call local service
		return resourceLocalService.countByType(type);
	}

	public long countByProvider(String userId, String provider) {
		// TODO check auth
		//
		// call local service
		return resourceLocalService.countByProvider(provider);
	}

	public long countByUserId(String userId, String ownerId) {
		// TODO check auth
		//
		// call local service
		return resourceLocalService.countByUserId(ownerId);
	}

	public long countByTypeAndUserId(String userId, String type, String ownerId) {
		// TODO check auth
		//
		// call local service
		return resourceLocalService.countByTypeAndUserId(type, ownerId);
	}

	public long countByProviderAndUserId(String userId, String provider, String ownerId) {
		// TODO check auth
		//
		// call local service
		return resourceLocalService.countByProviderAndUserId(provider, ownerId);
	}

	/*
	 * List
	 */

	public List<Resource> list(String userId) {
		// TODO check auth+filter
		//
		// call local service
		return resourceLocalService.list();
	}

	public List<Resource> list(String userId, int page, int pageSize) {
		// TODO check auth+filter
		//
		// call local service
		return list(userId, page, pageSize, "id", SystemKeys.ORDER_ASC);
	}

	public List<Resource> list(String userId, int page, int pageSize, String orderBy, String order) {
		// TODO check auth+filter
		//
		Sort sort = (order.equals(SystemKeys.ORDER_ASC) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending());
		Pageable pageable = PageRequest.of(page, pageSize, sort);
		// call local service
		return resourceLocalService.list(pageable);
	}

	public List<Resource> listByType(String userId, String type) {
		// TODO check auth+filter
		//
		// call local service
		return resourceLocalService.listByType(type);
	}

	public List<Resource> listByProvider(String userId, String provider) {
		// TODO check auth+filter
		//
		// call local service
		return resourceLocalService.listByProvider(provider);
	}

	public List<Resource> listByUserId(String userId, String ownerId) {
		// TODO check auth+filter
		//
		// call local service
		return resourceLocalService.listByUserId(ownerId);
	}

	public List<Resource> listByUserId(String userId, String ownerId, int page, int pageSize) {
		// TODO check auth+filter
		//
		// call local service
		return listByUserId(userId, ownerId, page, pageSize, "id", SystemKeys.ORDER_ASC);
	}

	public List<Resource> listByUserId(String userId, String ownerId, int page, int pageSize, String orderBy,
			String order) {
		// TODO check auth+filter
		//
		Sort sort = (order.equals(SystemKeys.ORDER_ASC) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending());
		Pageable pageable = PageRequest.of(page, pageSize, sort);
		// call local service
		return resourceLocalService.listByUserId(ownerId, pageable);
	}

	public List<Resource> listByTypeAndUserId(String userId, String type, String ownerId) {
		// TODO check auth+filter
		//
		// call local service
		return resourceLocalService.listByTypeAndUserId(type, ownerId);
	}

	public List<Resource> listByTypeAndUserId(String userId, String type, String ownerId, int page, int pageSize) {
		// TODO check auth+filter
		//
		// call local service
		return listByTypeAndUserId(userId, type, ownerId, page, pageSize, "id", SystemKeys.ORDER_ASC);
	}

	public List<Resource> listByTypeAndUserId(String userId, String type, String ownerId, int page, int pageSize,
			String orderBy,
			String order) {
		// TODO check auth+filter
		//
		Sort sort = (order.equals(SystemKeys.ORDER_ASC) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending());
		Pageable pageable = PageRequest.of(page, pageSize, sort);
		// call local service
		return resourceLocalService.listByTypeAndUserId(type, ownerId, pageable);
	}

	/*
	 * Check
	 */

	public void check(String userId, long id) throws NoSuchResourceException, NoSuchProviderException {
		// TODO check auth
		//
		// call local service
		resourceLocalService.check(id);

	}

}
