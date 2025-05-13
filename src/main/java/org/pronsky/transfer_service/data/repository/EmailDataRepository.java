package org.pronsky.transfer_service.data.repository;

import org.pronsky.transfer_service.data.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDataRepository extends JpaRepository<EmailData, Long> {

}
