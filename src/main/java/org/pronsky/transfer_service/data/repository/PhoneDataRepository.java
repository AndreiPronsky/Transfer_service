package org.pronsky.transfer_service.data.repository;

import org.pronsky.transfer_service.data.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {

    List<PhoneData> findPhoneDataByUserId(Long userId);
}
