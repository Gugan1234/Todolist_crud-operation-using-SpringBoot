package com.example.Book.controller;

import com.example.Book.model.Book;
import com.example.Book.repo.Bookrepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class Bookcontroller {
    @Autowired
    private Bookrepo bookRepo;

    @Operation(summary = "Get all the values")
    @ApiResponse(responseCode = "201", description = "Get all the values successfully")
    @GetMapping("/todos")
    public ResponseEntity<List<Book>> todos() {
        try{
            List<Book> booklist = new ArrayList<>();
            bookRepo.findAll().forEach(booklist::add);

            if (booklist.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(booklist, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "Get id the values")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "user fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Did not fetch properly")
    })
    @GetMapping("/todos/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> bookData = bookRepo.findById(id);
        if (bookData.isPresent()){
            return new ResponseEntity<>(bookData.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Operation(summary = "Post the values")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "user posted successfully"),
            @ApiResponse(responseCode = "404", description = "Did not posted properly")
    })
    @PostMapping("/todos")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book bookObj = bookRepo.save(book);

        return new ResponseEntity<>(bookObj, HttpStatus.OK);

    }

    @Operation(summary = "Patch the values")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "user patched successfully"),
            @ApiResponse(responseCode = "404", description = "Did not patch properly")
    })
    @PatchMapping("/todos/{id}")
    public ResponseEntity<Book> patchBook(@PathVariable Long id, @RequestBody Book book){
        Optional<Book> com = bookRepo.findById(id);
        com.get().setCompleted(book.getCompleted());
        Book bookObj = bookRepo.save(com.get());
        ResponseEntity<Book> response = new ResponseEntity<>(bookObj, HttpStatus.OK);
        return response;
    }

    @Operation(summary = "Update the value")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "user updated successfully"),
            @ApiResponse(responseCode = "404", description = "Did not updated properly")
    })
    @PostMapping("/Update/todos/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody Book newBookData){
        Optional<Book> oldBookData = bookRepo.findById(id);
        if (oldBookData.isPresent()){
            Book updatedBookData = oldBookData.get();
            updatedBookData.setTitle(newBookData.getTitle());
            updatedBookData.setDescription(newBookData.getDescription());
            updatedBookData.setCompleted(newBookData.getCompleted());
            updatedBookData.setCreatedAt(newBookData.getCreatedAt());

            Book bookObj = bookRepo.save(updatedBookData);
            return new ResponseEntity<>(bookObj, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Operation(summary = "Delete the value with id")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "user deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Did not delete properly")
    })

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id){
        bookRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
