package com.example.redistest.controller;

import com.example.redistest.configuration.StudentKey;
import com.example.redistest.student.StudentEntity;
import com.example.redistest.student.StudentRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {

  private final StudentRepository repository;
  private final RedisTemplate<String, StudentEntity> redisTemplate;

  public RedisController(StudentRepository repository, RedisTemplate<String, StudentEntity> redisTemplate) {
    this.repository = repository;
    this.redisTemplate = redisTemplate;
  }

  @GetMapping("/customer/{id}")
  public ResponseEntity<String> getCustomer(@PathVariable("id") String id) {
    log.info("Szukam ID {}", id);
    Optional<StudentEntity> byId = repository.findById(new StudentKey(id, "30"));
    return new ResponseEntity<>(byId.map(Object::toString).orElse("Nie ma mnie"), HttpStatus.OK);
  }

  @GetMapping("/intialize/{id}/{name}")
  public ResponseEntity<String> initialize(@PathVariable("id") String id,
                                           @PathVariable("name") String name) {
    repository.save(new StudentEntity(new StudentKey(id,"30"), name, 20L));
    return new ResponseEntity<>("Utworzono", HttpStatus.OK);
  }

  @GetMapping("/ttl")
  public ResponseEntity<String> initialize() {
    return new ResponseEntity<>("Utworzono", HttpStatus.OK);
  }
}
