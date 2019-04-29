package it.smartcommunitylab.storagemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.smartcommunitylab.storagemanager.model.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

}
