package it.smartcommunitylab.storagemanager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.storagemanager.SystemKeys;
import it.smartcommunitylab.storagemanager.common.NoSuchConsumerException;
import it.smartcommunitylab.storagemanager.common.NoSuchProviderException;
import it.smartcommunitylab.storagemanager.common.NoSuchResourceException;
import it.smartcommunitylab.storagemanager.config.ConsumerConfiguration;
import it.smartcommunitylab.storagemanager.model.Consumer;
import it.smartcommunitylab.storagemanager.model.ConsumerBuilder;
import it.smartcommunitylab.storagemanager.model.Registration;
import it.smartcommunitylab.storagemanager.model.Resource;
import it.smartcommunitylab.storagemanager.model.ResourceEvent;
import it.smartcommunitylab.storagemanager.repository.RegistrationRepository;

@Component
public class ConsumerLocalService {
	private final static Logger _log = LoggerFactory.getLogger(ConsumerLocalService.class);

	private Map<String, List<Consumer>> _consumers;

	@Autowired
	private ConsumerConfiguration staticConfig;

	@Autowired
	private RegistrationRepository registrationRepository;

	@Autowired
	private ResourceLocalService resourceLocalService;

	/*
	 * Init
	 */
	@PostConstruct
	private void init() {
		// init consumer in-memory map
		// TODO replace with better repo
		_consumers = new HashMap<>();
		// init types - TODO add configuration
		_consumers.put(SystemKeys.TYPE_SQL, new ArrayList<>());
		_consumers.put(SystemKeys.TYPE_NOSQL, new ArrayList<>());
		_consumers.put(SystemKeys.TYPE_FILE, new ArrayList<>());
		_consumers.put(SystemKeys.TYPE_OBJECT, new ArrayList<>());

		// scan static consumers from config
		// TODO refactor
		for (String id : staticConfig.getSql()) {
			// create via builder
			try {
				Consumer c = buildConsumer(id);
				// add to map
				_consumers.get(SystemKeys.TYPE_SQL).add(c);
			} catch (NoSuchConsumerException e) {
				_log.error("no builder for " + id);
			}
		}

		for (String id : staticConfig.getNosql()) {
			// create via builder
			try {
				Consumer c = buildConsumer(id);
				// add to map
				_consumers.get(SystemKeys.TYPE_NOSQL).add(c);
			} catch (NoSuchConsumerException e) {
				_log.error("no builder for " + id);
			}
		}

		for (String id : staticConfig.getFile()) {
			// create via builder
			try {
				Consumer c = buildConsumer(id);
				// add to map
				_consumers.get(SystemKeys.TYPE_FILE).add(c);
			} catch (NoSuchConsumerException e) {
				_log.error("no builder for " + id);
			}
		}

		for (String id : staticConfig.getObject()) {
			// create via builder
			try {
				Consumer c = buildConsumer(id);
				// add to map
				_consumers.get(SystemKeys.TYPE_OBJECT).add(c);
			} catch (NoSuchConsumerException e) {
				_log.error("no builder for " + id);
			}
		}

		// read consumers from DB
		List<Registration> registrations = registrationRepository.findAll();
		for (Registration reg : registrations) {
			// build consumer with properties
			try {
				Consumer c = buildConsumer(reg);
				// add to map
				_consumers.get(reg.getType()).add(c);
			} catch (NoSuchConsumerException e) {
				_log.error("no builder for registration " + reg.getId());
			}
		}

		// check all resources
		checkResources();
	}

	public void checkResources() {
		// fetch all resources and require check for both producer and consumers
		List<Resource> resources = resourceLocalService.list();
		for (Resource r : resources) {
			try {
				resourceLocalService.check(r.getId());
			} catch (NoSuchResourceException | NoSuchProviderException e) {
				_log.error("error checking resource " + r.getId());
			}
		}
	}

	/*
	 * Builders
	 */

	@Autowired
	private Map<String, ConsumerBuilder> _builders;

	private Consumer buildConsumer(String id) throws NoSuchConsumerException {

		// lookup builder
		String builderClass = id.replace("Consumer", "Builder");

		if (!_builders.containsKey(builderClass)) {
			throw new NoSuchConsumerException();
		}

		_log.info("build " + id + " via " + builderClass);

		return _builders.get(builderClass).build();
	}

	private Consumer buildConsumer(Registration reg) throws NoSuchConsumerException {

		// lookup builder
		String id = reg.getConsumer();
		String builderClass = id.replace("Consumer", "Builder");

		if (!_builders.containsKey(builderClass)) {
			throw new NoSuchConsumerException();
		}

		_log.info("build " + id + " via " + builderClass);

		return _builders.get(builderClass).build(reg);
	}

	/*
	 * Data
	 */

	/*
	 * Events
	 */
	@EventListener
	public void receiveResourceEvent(ResourceEvent event) {
		_log.info("receive message for " + event.getType() + " with payload " + event.getAction() + ":"
				+ String.valueOf(event.getId()));

		try {
			// fetch resource
			Resource res = resourceLocalService.get(event.getId());
			String type = event.getType();
			String action = event.getAction();
			String userId = event.getUserId();

			// notify all active consumers
			// note: filtering by user is performed by consumers, IF required/implemented
			for (Consumer c : _consumers.get(type)) {
				if (c.getStatus() == SystemKeys.STATUS_READY) {
					_log.info("notify consumer " + c.getId() + " for " + event.getType() + " with payload "
							+ event.getAction() + ":" + String.valueOf(event.getId()));

					switch (action) {
					case SystemKeys.ACTION_CREATE:
						c.addResource(userId, res);
						break;
					case SystemKeys.ACTION_UPDATE:
						c.updateResource(userId, res);
						break;
					case SystemKeys.ACTION_DELETE:
						c.deleteResource(userId, res);
						break;
					case SystemKeys.ACTION_CHECK:
						c.checkResource(userId, res);
						break;
					}

				}
			}

		} catch (NoSuchResourceException e) {
			_log.error("resource missing for event");
		}

	}
}
