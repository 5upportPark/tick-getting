package com.pjw.tickgettinig.actor;

import com.pjw.tickgettinig.actor.vo.ActorView;
import com.pjw.tickgettinig.actor.vo.AddActorReq;
import com.pjw.tickgettinig.actor.vo.UpdateActorReq;
import com.pjw.tickgettinig.common.ApiResponseExamples;
import com.pjw.tickgettinig.common.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actor")
@RequiredArgsConstructor
@Tag(name = "출연진 API", description = "출연진 정보 조회 API")
public class ActorController {
    private final ActorService actorService;

    @GetMapping
    @Operation(summary = "출연진 리스트 조회")
    @ApiResponseExamples({ErrorCode.RESOURCE_NOT_FOUND})
    public List<ActorView> getActorList(@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber
            , @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
            , @RequestParam(name = "searchText", required = false) String searchText
            , @RequestParam(name = "searchType", required = false) String searchType)
    {
        return actorService.getActorList(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    @Operation(summary = "출연진 조회")
    public ActorView getActor(@PathVariable("id") Long id){
        return actorService.getActor(id);
    }

    @Operation(summary = "출연진 추가")
    @PostMapping
    public void saveActor(@Valid @RequestBody AddActorReq req){
        actorService.saveActor(req);
    }

    @Operation(summary = "출연진 수정")
    @PutMapping
    public void updateActor(@Valid @RequestBody UpdateActorReq req){
        actorService.updateActor(req);
    }

    @Operation(summary = "출연진 수정")
    @DeleteMapping("/{id}")
    public void deleteActor(@PathVariable Long id){
        actorService.deleteActor(id);
    }
}
