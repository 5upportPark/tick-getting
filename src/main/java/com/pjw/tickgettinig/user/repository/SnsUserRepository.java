package com.pjw.tickgettinig.user.repository;

import com.pjw.tickgettinig.oauth.SnsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnsUserRepository extends JpaRepository<SnsUser, Long> {

}
