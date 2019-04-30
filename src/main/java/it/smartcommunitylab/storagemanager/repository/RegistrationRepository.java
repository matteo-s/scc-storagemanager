package it.smartcommunitylab.storagemanager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import it.smartcommunitylab.storagemanager.model.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

	Long countByType(String type);

	Long countByConsumer(String consumer);

	Long countByUserId(String userId);

	Long countByTypeAndUserId(String type, String userId);

	Long countByConsumerAndUserId(String consumer, String userId);

	List<Registration> findByType(String type);

	List<Registration> findByType(String type, Sort sort);

	Page<Registration> findByType(String type, Pageable pageable);

	List<Registration> findByUserId(String userId);

	List<Registration> findByUserId(String userId, Sort sort);

	Page<Registration> findByUserId(String userId, Pageable pageable);

	List<Registration> findByConsumer(String consumer);

	List<Registration> findByConsumer(String consumer, Sort sort);

	Page<Registration> findByConsumer(String consumer, Pageable pageable);

	List<Registration> findByTypeAndUserId(String type, String userId);

	List<Registration> findByTypeAndUserId(String type, String userId, Sort sort);

	Page<Registration> findByTypeAndUserId(String type, String userId, Pageable pageable);

	List<Registration> findByConsumerAndUserId(String consumer, String userId);

	List<Registration> findByConsumerAndUserId(String consumer, String userId, Sort sort);

	Page<Registration> findByConsumerAndUserId(String consumer, String userId, Pageable pageable);
}
