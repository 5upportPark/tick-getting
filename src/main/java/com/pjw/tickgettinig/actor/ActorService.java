package com.pjw.tickgettinig.actor;

import com.pjw.tickgettinig.actor.entity.Actor;
import com.pjw.tickgettinig.actor.repository.ActorRepositoryImpl;
import com.pjw.tickgettinig.actor.vo.ActorView;
import com.pjw.tickgettinig.actor.vo.AddActorReq;
import com.pjw.tickgettinig.actor.vo.UpdateActorReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {
    private final ActorRepositoryImpl actorRepository;

    public ActorService(ActorRepositoryImpl actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<ActorView> getActorList(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(Sort.Order.desc("id")));
        Page<Actor> actorPage = actorRepository.findAll(pageable);
        List<Actor> actorList = actorPage.getContent();

        return actorList.stream().map(Actor::toView).toList();
    }

    public ActorView getActor(Long id){
        Actor actor = actorRepository.findById(id).orElseThrow(RuntimeException::new);
        return ActorView.from(actor.getId(), actor.getName(), actor.getImage());
    }

    public ActorView saveActor(AddActorReq req){
        Actor actor = Actor.from(req);
        actorRepository.save(actor);

        return actor.toView();
    }

    public ActorView updateActor(UpdateActorReq req){
        // actor 정보가 존재하는 경우에만 수정
        Actor actor = actorRepository.findById(req.getId()).orElseThrow(RuntimeException::new);

        actor.update(req.getName(), req.getImage());
        actorRepository.save(actor);

        return actor.toView();
    }

    public void deleteActor(Long actorId){
        actorRepository.deleteById(actorId);
    }
}
