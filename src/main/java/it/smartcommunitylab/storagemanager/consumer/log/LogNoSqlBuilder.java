package it.smartcommunitylab.storagemanager.consumer.log;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.storagemanager.common.NoSuchConsumerException;
import it.smartcommunitylab.storagemanager.model.Consumer;
import it.smartcommunitylab.storagemanager.model.ConsumerBuilder;
import it.smartcommunitylab.storagemanager.model.Registration;

@Component
public class LogNoSqlBuilder implements ConsumerBuilder {

	@Value("${consumers.log.enable}")
	private boolean enabled;

	private static LogNoSqlConsumer _instance;

	@Override
	public Consumer build() throws NoSuchConsumerException {
		if (!enabled) {
			throw new NoSuchConsumerException();
		}

		// use singleton
		if (_instance == null) {
			_instance = new LogNoSqlConsumer();
			// explicitly call init() since @postconstruct won't work here
			_instance.init();

		}

		return _instance;
	}

	@Override
	public Consumer build(JSONObject properties) throws NoSuchConsumerException {
		return build();
	}

	@Override
	public Consumer build(Registration reg) throws NoSuchConsumerException {
		return build();
	}

}
