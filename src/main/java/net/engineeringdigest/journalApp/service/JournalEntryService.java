package net.engineeringdigest.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

	
	
	@Autowired
	private JournalEntryRepository journalEntryRepository;

	@Autowired
	private UserService userService;

	@Transactional
	public void saveEntry(JournalEntry journalEntry, String username) {
		try {
			User user = userService.findByUsername(username);
			journalEntry.setDate(LocalDateTime.now());
			JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
			user.getJournalEntries().add(savedEntry);
			userService.saveUser(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("An error occured while saving entry, " + e);
		}
	}

	public void saveEntry(JournalEntry journalEntry) {
		journalEntryRepository.save(journalEntry);
	}

	public List<JournalEntry> getAll() {
		return journalEntryRepository.findAll();
	}

	public Optional<JournalEntry> findById(ObjectId id) {
		return journalEntryRepository.findById(id);
	}

	@Transactional
	public boolean deleteById(ObjectId id, String username) {
		boolean removed = false;
		try {
			User user = userService.findByUsername(username);
			removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
			if (removed) {
				userService.saveUser(user);
				journalEntryRepository.deleteById(id);
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("An error occured while deleting the journal id");
		}
		return removed;

	}

	public List<JournalEntry> findByUserName(String username) {
		return null;
	}
}
