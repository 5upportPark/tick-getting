package com.pjw.tickgettinig.actor.repository;

import com.pjw.tickgettinig.actor.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor,Long> {
}
