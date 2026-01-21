package com.pjw.tickgettinig.user.repository;

import com.pjw.tickgettinig.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u LEFT JOIN FETCH u.snsUser WHERE u.id = :id")
  Optional<User> findById(Long id);

  Optional<User> findByUsername(String name);

  Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
