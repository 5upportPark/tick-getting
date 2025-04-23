package com.pjw.tickgettinig.actor;

import com.pjw.tickgettinig.actor.vo.ActorView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actor")
@RequiredArgsConstructor
public class ActorController {
    private ActorService actorService;

    @GetMapping
    public List<ActorView> getActorList(@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber
            , @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
            , @RequestParam(name = "searchText", required = false) String searchText
            , @RequestParam(name = "searchType", required = false) String searchType)
    {
        return actorService.getActorList(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public ActorView getActor(@PathVariable("id") Long id){
        return actorService.getActor(id);
    }
}
