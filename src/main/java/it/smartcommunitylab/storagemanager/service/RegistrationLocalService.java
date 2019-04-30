package it.smartcommunitylab.storagemanager.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.storagemanager.common.NoSuchConsumerException;
import it.smartcommunitylab.storagemanager.common.NoSuchRegistrationException;
import it.smartcommunitylab.storagemanager.model.Registration;
import it.smartcommunitylab.storagemanager.repository.RegistrationRepository;

@Component
public class RegistrationLocalService {

	@Autowired
	private RegistrationRepository registrationRepository;

	/*
	 * Data
	 */

	public Registration add(String userId, String type, String consumer, Map<String, Serializable> properties)
			throws NoSuchConsumerException {

		// build registration
		Registration reg = new Registration();
		reg.setUserId(userId);
		reg.setType(type);
		reg.setConsumer(consumer);
		reg.setPropertiesMap(properties);

		// save registration
		return registrationRepository.saveAndFlush(reg);
	}

	public void delete(long id) throws NoSuchRegistrationException {

		// clear registration
		registrationRepository.deleteById(id);
	}

	public Registration get(long id) throws NoSuchRegistrationException {
		// fetch registration
		Optional<Registration> r = registrationRepository.findById(id);

		if (!r.isPresent()) {
			throw new NoSuchRegistrationException();
		}

		return r.get();
	}

	/*
	 * Count
	 */
	public long count() {
		return registrationRepository.count();
	}

	public long countByType(String type) {
		return registrationRepository.countByType(type);
	}

	public long countByConsumer(String provider) {
		return registrationRepository.countByConsumer(provider);
	}

	public long countByUserId(String userId) {
		return registrationRepository.countByUserId(userId);
	}

	public long countByTypeAndUserId(String type, String userId) {
		return registrationRepository.countByTypeAndUserId(type, userId);
	}

	public long countByConsumerAndUserId(String provider, String userId) {
		return registrationRepository.countByConsumerAndUserId(provider, userId);
	}
	/*
	 * List
	 */

	public List<Registration> list() {
		return registrationRepository.findAll();
	}

	public List<Registration> list(Pageable pageable) {
		Page<Registration> result = registrationRepository.findAll(pageable);
		return result.getContent();
	}

	public List<Registration> listByType(String type) {
		return registrationRepository.findByType(type);
	}

	public List<Registration> listByConsumer(String provider) {
		return registrationRepository.findByConsumer(provider);
	}

	public List<Registration> listByUserId(String userId) {
		return registrationRepository.findByUserId(userId);
	}

	public List<Registration> listByUserId(String userId, Pageable pageable) {
		Page<Registration> result = registrationRepository.findByUserId(userId, pageable);
		return result.getContent();
	}

	public List<Registration> listByTypeAndUserId(String type, String userId) {
		return registrationRepository.findByTypeAndUserId(type, userId);
	}

	public List<Registration> listByTypeAndUserId(String type, String userId, Pageable pageable) {
		Page<Registration> result = registrationRepository.findByTypeAndUserId(type, userId, pageable);
		return result.getContent();
	}

}
