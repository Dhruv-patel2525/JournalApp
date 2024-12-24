package com.learning.journalApp.controller;

import com.learning.journalApp.entity.JournalEntry;
import com.learning.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @GetMapping("/{userName}")
    public ResponseEntity<?> getAll(@PathVariable String userName)
    {
        try {
            List<JournalEntry> all = journalEntryService.findAll(userName);
            if (all != null && !all.isEmpty()) {
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
            return new ResponseEntity<>("List is Empty",HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>("Not able to fetch Journal for Users",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getElementByID(@PathVariable ObjectId id)
    {
        JournalEntry journalEntry= journalEntryService.findByID(id).orElse(null);
        if(journalEntry!=null)
        {
            return new ResponseEntity<>(journalEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>("ID not found",HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> createJournalEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName)
    {
        try {
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error in creating journal",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{userName}/{id}")
    public ResponseEntity<?> updatedByID(@PathVariable ObjectId id,@RequestBody JournalEntry myEntry,@PathVariable String userName)
    {
        try {
             JournalEntry newEntry = journalEntryService.updateByID(id,myEntry,userName);
             return  new ResponseEntity<>(newEntry,HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>("Id not Found",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{userName}/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable ObjectId id,@PathVariable String userName)
    {
        try {
             boolean isDeleted=journalEntryService.deleteByID(id,userName);
             return new ResponseEntity<>(isDeleted,HttpStatus.NO_CONTENT);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>("ID not found",HttpStatus.NOT_FOUND);
        }
    }
}
