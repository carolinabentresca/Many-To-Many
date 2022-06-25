package com.example.repository;

import com.example.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {

    List<Libro> findLibrosByAutoresId(int autorId);
}
