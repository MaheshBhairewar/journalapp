package net.engineeringdigest.journalApp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JourneyEntryController {

	@Autowired
	private JournalEntryService journalEntryService;

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username);
		List<JournalEntry> all = user.getJournalEntries();
		if (null != all & !all.isEmpty())
			return new ResponseEntity<List<JournalEntry>>(journalEntryService.getAll(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			journalEntryService.saveEntry(entry, username);
			return new ResponseEntity<>(entry, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username);
		List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			Optional<JournalEntry> entry = journalEntryService.findById(myId);
			if (entry.isPresent()) {
				return new ResponseEntity<JournalEntry>(entry.get(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@DeleteMapping("/id/{myId}")
	public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		boolean removed = journalEntryService.deleteById(myId, username);
		return removed ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId myId,
			@RequestBody JournalEntry newEntry) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username);
		List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			Optional<JournalEntry> entry = journalEntryService.findById(myId);
			if (entry.isPresent()) {
				JournalEntry oldEntry = entry.get();
				oldEntry.setTitle(null != newEntry.getTitle() && !"".equals(newEntry.getTitle()) ? newEntry.getTitle()
						: oldEntry.getTitle());
				oldEntry.setContent(
						null != newEntry.getContent() && !"".equals(newEntry.getContent()) ? newEntry.getContent()
								: oldEntry.getContent());
				journalEntryService.saveEntry(oldEntry);
				return new ResponseEntity<JournalEntry>(oldEntry, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
