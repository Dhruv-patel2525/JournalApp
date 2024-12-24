package com.learning.journalApp.service;

import com.learning.journalApp.entity.JournalEntry;
import com.learning.journalApp.entity.User;
import com.learning.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName)
    {
        User user=userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry save = journalEntryRepository.save(journalEntry);
        user.getJournalentries().add(save);
        userService.createUser(user);
    }

    public List<JournalEntry> findAll(String userName)
    {
        User user=userService.findByUserName(userName);
        return user.getJournalentries();
        //return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findByID(ObjectId id)
    {
        return journalEntryRepository.findById(id);
    }

    public boolean deleteByID(ObjectId id, String userName)
    {
        User user=userService.findByUserName(userName);
        journalEntryRepository.deleteById(id);
        user.getJournalentries().removeIf(x -> x.getId().equals(id));
        userService.createUser(user);
        return true;
    }

    public JournalEntry updateByID(ObjectId id, JournalEntry newEntry, String userName)
    {

        JournalEntry oldEntry = journalEntryRepository.findById(id).orElse(null);
        if(oldEntry!=null)
        {
            oldEntry.setDescription(newEntry.getDescription()!=null && !newEntry.getDescription().equals("")? newEntry.getDescription() : oldEntry.getDescription());
            oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): oldEntry.getTitle());
            journalEntryRepository.save(oldEntry);
        }
        return oldEntry;
    }
}
