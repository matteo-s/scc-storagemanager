package it.smartcommunitylab.storagemanager.consumer.local;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.storagemanager.common.NoSuchConsumerException;
import it.smartcommunitylab.storagemanager.model.Consumer;
import it.smartcommunitylab.storagemanager.model.ConsumerBuilder;
import it.smartcommunitylab.storagemanager.model.Registration;

@Component
public class NullSqlBuilder implements ConsumerBuilder {

	@Value("${consumers.null.enable}")
	private boolean enabled;

	@Override
	public String getType() {
		return NullSqlConsumer.TYPE;
	}

	@Override
	public String getId() {
		return NullSqlConsumer.ID;
	}

	@Override
	public boolean isAvailable() {
		return enabled;
	}

	@Override
	public Consumer build() throws NoSuchConsumerException {
		if (!enabled) {
			throw new NoSuchConsumerException();
		}

		NullSqlConsumer consumer = new NullSqlConsumer();
		// explicitly call init() since @postconstruct won't work here
		consumer.init();

		return consumer;
	}

	@Override
	public Consumer build(Map<String, Serializable> properties) throws NoSuchConsumerException {
		// properties supported
		NullSqlConsumer consumer = new NullSqlConsumer(properties);
		// explicitly call init() since @postconstruct won't work here
		consumer.init();

		return consumer;
	}

	@Override
	public Consumer build(Registration reg) throws NoSuchConsumerException {
		// properties supported
		NullSqlConsumer consumer = new NullSqlConsumer(reg.getPropertiesMap());
		// explicitly call init() since @postconstruct won't work here
		consumer.init();

		return consumer;
	}

}
