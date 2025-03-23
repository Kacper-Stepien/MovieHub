package com.example.movies_api.crew;

import com.example.movies_api.flyweight.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crew")
@RequiredArgsConstructor
public class CrewController {
    private final CrewService crewService;

    @PostMapping("/init-root")
    public ResponseEntity<Long> initCrewRoot(@RequestParam Long movieId) {
        Long rootId = crewService.initCrewRoot(movieId);
        return ResponseEntity.ok(rootId);
    }

    @PostMapping("/add-member")
    public ResponseEntity<Long> addMember(@RequestParam Long parentGroupId,
                                          @RequestParam String name,
                                          @RequestParam String role) {
        RoleName roleName = RoleName.valueOf(role);
        Long memberId = crewService.addCrewMember(parentGroupId, name, roleName);
        return ResponseEntity.ok(memberId);
    }

    @PostMapping("/add-group")
    public ResponseEntity<Long> addGroup(@RequestParam Long parentGroupId,
                                         @RequestParam String groupName) {
        Long groupId = crewService.addCrewGroup(parentGroupId, groupName);
        return ResponseEntity.ok(groupId);
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<String> showTree(@PathVariable Long id) {
        return ResponseEntity.ok(crewService.showCrewTree(id));
    }

    @GetMapping("/{id}/count")
    public ResponseEntity<Integer> count(@PathVariable Long id) {
        return ResponseEntity.ok(crewService.countCrew(id));
    }
}
