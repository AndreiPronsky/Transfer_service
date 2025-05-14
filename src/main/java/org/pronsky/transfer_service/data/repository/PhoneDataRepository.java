package org.pronsky.transfer_service.data.repository;

import org.pronsky.transfer_service.data.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {

    Set<PhoneData> findAllPhoneDataByUserId(Long userId);

    PhoneData findByIdAndUserId(Long phoneNumberId, Long userId);

    void deleteByIdAndUserId(Long phoneNumberId, Long userId);
}
