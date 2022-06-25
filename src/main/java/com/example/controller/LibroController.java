package com.example.controller;

import com.example.model.Libro;
import com.example.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/libro")
@CrossOrigin(origins = "http://localhost:8080")
public class LibroController {

    @Autowired
    LibroRepository libroRepository;

    @GetMapping
    public ResponseEntity<List<Libro>> list() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Libro> save(@RequestBody Libro libro) {
        Libro lib = libroRepository.save(libro);
        return new ResponseEntity<>(lib, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        libroRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
