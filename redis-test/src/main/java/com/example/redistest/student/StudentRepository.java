package com.example.redistest.student;

import com.example.redistest.configuration.StudentKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, StudentKey> {
}
