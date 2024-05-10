package springboot.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.dto.InternshipDto;
import springboot.entities.InternshipEntity;
import springboot.services.InternshipService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/internships")
public class InternshipController {
    InternshipService internshipService;

    /**
     * Возвращает стажировку по ID
     */
    @ApiOperation(value = "Возвращает стажировку по ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getInternship(@PathVariable Long id) {
        InternshipEntity entity = internshipService.getInternshipById(id);
        return ResponseEntity.ok(entity);
    }

    /**
     * Создаёт стажировку
     */
    @ApiOperation(value = "Создаёт стажировку")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> add(@RequestBody InternshipDto requestObject) {
        InternshipEntity entity = internshipService.save(requestObject);
        return ResponseEntity.ok(entity);
    }


    public ResponseEntity<?> update(@PathVariable Long internshipId, @RequestBody InternshipDto internshipDto) {
        InternshipEntity internshipEntity = internshipService.update(internshipDto, internshipId);
        return ResponseEntity.ok(internshipEntity);
    }
}
