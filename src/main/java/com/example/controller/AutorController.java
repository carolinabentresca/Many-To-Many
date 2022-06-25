package com.example.controller;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Autor;
import com.example.model.Libro;
import com.example.repository.AutorRepository;
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
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping("/autor")
    public ResponseEntity<List<Autor>> list() {
        List<Autor> autores = new ArrayList();
        autorRepository.findAll().forEach(autores::add);

        if (autores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(autores, HttpStatus.OK);
    }

    @GetMapping("/libro/{libroId}/autor")
    public ResponseEntity<List<Autor>> getAllAutoresByLibroId(@PathVariable(value = "libroId") int id) {
        if (!libroRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<Autor> autores = autorRepository.findAutoresByLibrosId(id);
        return new ResponseEntity<>(autores, HttpStatus.OK);
    }

    @GetMapping("/autor/{autorId}/libro")
    public ResponseEntity<List<Libro>> getAllLibrosByAutorId(@PathVariable(value = "autorId") int id) {
        if (!autorRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<Libro> libros = libroRepository.findLibrosByAutoresId(id);
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        autorRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/libro/{libroId}/autor")
    public ResponseEntity<Autor> add(@PathVariable(value = "libroId") int id, @RequestBody Autor autorRequest) {
        Autor autor = libroRepository.findById(id).map(libro -> {
            int autorId = autorRequest.getId();
            if (autorId != 0L) {
                Autor au = autorRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Autor with id = " + id));
                libro.addAutor(au);
                libroRepository.save(libro);
                return au;
            }
            //create Autor
            libro.addAutor(autorRequest);
            return autorRepository.save(autorRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Libro with id = " + id));

        return new ResponseEntity<>(autor, HttpStatus.CREATED);
    }

}
