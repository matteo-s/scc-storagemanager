package it.smartcommunitylab.storagemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import it.smartcommunitylab.storagemanager.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

	Long countByType(String type);

	Long countByProvider(String provider);

	Long countByUserId(String userId);

	Long countByTypeAndUserId(String type, String userId);

	Long countByProviderAndUserId(String provider, String userId);

	List<Resource> findByType(String type);

	List<Resource> findByType(String type, Sort sort);

	Page<Resource> findByType(String type, Pageable pageable);

	List<Resource> findByUserId(String userId);

	List<Resource> findByUserId(String userId, Sort sort);

	Page<Resource> findByUserId(String userId, Pageable pageable);

	List<Resource> findByProvider(String provider);

	List<Resource> findByProvider(String provider, Sort sort);

	Page<Resource> findByProvider(String provider, Pageable pageable);

	List<Resource> findByTypeAndUserId(String type, String userId);

	List<Resource> findByTypeAndUserId(String type, String userId, Sort sort);

	Page<Resource> findByTypeAndUserId(String type, String userId, Pageable pageable);

	List<Resource> findByProviderAndUserId(String provider, String userId);

	List<Resource> findByProviderAndUserId(String provider, String userId, Sort sort);

	Page<Resource> findByProviderAndUserId(String provider, String userId, Pageable pageable);
}
