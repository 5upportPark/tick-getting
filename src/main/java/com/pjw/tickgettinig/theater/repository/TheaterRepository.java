package com.pjw.tickgettinig.theater.repository;

import com.pjw.tickgettinig.theater.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

}
