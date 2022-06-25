package com.example.repository;

import com.example.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {

    List<Autor> findAutoresByLibrosId(int libroId);
}
