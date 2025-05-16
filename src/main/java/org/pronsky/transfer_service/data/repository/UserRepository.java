package org.pronsky.transfer_service.data.repository;

import org.pronsky.transfer_service.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    boolean existsByPrimaryEmail(String username);

    Optional<User> findByPrimaryEmail(String username);
}
