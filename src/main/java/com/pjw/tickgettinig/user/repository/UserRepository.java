package com.pjw.tickgettinig.user.repository;

import com.pjw.tickgettinig.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String name);

  Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
