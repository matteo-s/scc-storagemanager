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
import it.smartcommunitylab.storagemanager.common.NoSuchConsumerException;
import it.smartcommunitylab.storagemanager.common.NoSuchRegistrationException;
import it.smartcommunitylab.storagemanager.model.Registration;

@Component
public class ConsumerService {
	private final static Logger _log = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	private ConsumerLocalService consumerService;

	@Autowired
	private RegistrationLocalService registrationService;

	/*
	 * Data
	 */

	public Registration add(String userId, String type, String consumer,
			Map<String, Serializable> properties)
			throws NoSuchConsumerException {
		// TODO check auth
		//
		// call local service
		return consumerService.add(userId, type, consumer, properties);
	}

	public void delete(String userId, long id) throws NoSuchConsumerException {
		// TODO check auth
		//
		// call local service
		consumerService.delete(id);
	}

	public Registration get(String userId, long id) throws NoSuchConsumerException {

		try {
			// TODO check auth
			//
			// call local service
			return registrationService.get(id);
		} catch (NoSuchRegistrationException e) {
			throw new NoSuchConsumerException();
		}
	}

	/*
	 * Count
	 */

	public long count(String userId) {
		// TODO check auth
		//
		// call local service
		return registrationService.count();
	}

	public long countByType(String userId, String type) {
		// TODO check auth
		//
		// call local service
		return registrationService.countByType(type);
	}

	public long countByConsumer(String userId, String provider) {
		// TODO check auth
		//
		// call local service
		return registrationService.countByConsumer(provider);
	}

	public long countByUserId(String userId, String ownerId) {
		// TODO check auth
		//
		// call local service
		return registrationService.countByUserId(ownerId);
	}

	public long countByTypeAndUserId(String userId, String type, String ownerId) {
		// TODO check auth
		//
		// call local service
		return registrationService.countByTypeAndUserId(type, ownerId);
	}

	public long countByConsumerAndUserId(String userId, String provider, String ownerId) {
		// TODO check auth
		//
		// call local service
		return registrationService.countByConsumerAndUserId(provider, ownerId);
	}

	/*
	 * List
	 */

	public List<Registration> list(String userId) {
		// TODO check auth+filter
		//
		// call local service
		return registrationService.list();
	}

	public List<Registration> list(String userId, int page, int pageSize) {
		// TODO check auth+filter
		//
		// call local service
		return list(userId, page, pageSize, "id", SystemKeys.ORDER_ASC);
	}

	public List<Registration> list(String userId, int page, int pageSize, String orderBy, String order) {
		// TODO check auth+filter
		//
		Sort sort = (order.equals(SystemKeys.ORDER_ASC) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending());
		Pageable pageable = PageRequest.of(page, pageSize, sort);
		// call local service
		return registrationService.list(pageable);
	}

	public List<Registration> listByType(String userId, String type) {
		// TODO check auth+filter
		//
		// call local service
		return registrationService.listByType(type);
	}

	public List<Registration> listByConsumer(String userId, String provider) {
		// TODO check auth+filter
		//
		// call local service
		return registrationService.listByConsumer(provider);
	}

	public List<Registration> listByUserId(String userId, String ownerId) {
		// TODO check auth+filter
		//
		// call local service
		return registrationService.listByUserId(ownerId);
	}

	public List<Registration> listByUserId(String userId, String ownerId, int page, int pageSize) {
		// TODO check auth+filter
		//
		// call local service
		return listByUserId(userId, ownerId, page, pageSize, "id", SystemKeys.ORDER_ASC);
	}

	public List<Registration> listByUserId(String userId, String ownerId, int page, int pageSize, String orderBy,
			String order) {
		// TODO check auth+filter
		//
		Sort sort = (order.equals(SystemKeys.ORDER_ASC) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending());
		Pageable pageable = PageRequest.of(page, pageSize, sort);
		// call local service
		return registrationService.listByUserId(ownerId, pageable);
	}

	public List<Registration> listByTypeAndUserId(String userId, String type, String ownerId) {
		// TODO check auth+filter
		//
		// call local service
		return registrationService.listByTypeAndUserId(type, ownerId);
	}

	public List<Registration> listByTypeAndUserId(String userId, String type, String ownerId, int page, int pageSize) {
		// TODO check auth+filter
		//
		// call local service
		return listByTypeAndUserId(userId, type, ownerId, page, pageSize, "id", SystemKeys.ORDER_ASC);
	}

	public List<Registration> listByTypeAndUserId(String userId, String type, String ownerId, int page, int pageSize,
			String orderBy,
			String order) {
		// TODO check auth+filter
		//
		Sort sort = (order.equals(SystemKeys.ORDER_ASC) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending());
		Pageable pageable = PageRequest.of(page, pageSize, sort);
		// call local service
		return registrationService.listByTypeAndUserId(type, ownerId, pageable);
	}
}
