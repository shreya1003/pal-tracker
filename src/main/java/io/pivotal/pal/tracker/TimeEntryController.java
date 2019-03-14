package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TimeEntryController {


    TimeEntryRepository timeEntryRepository;

    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(@Qualifier("JDBCRepository") TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {

        TimeEntry newTimeEntry = timeEntryRepository.create(timeEntry);

        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());

        return new ResponseEntity<TimeEntry>(newTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        if (timeEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        actionCounter.increment();

        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
    }


    @PutMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable("id") long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry updatedTimeEntry = timeEntryRepository.update(timeEntryId, expected);

        if (updatedTimeEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        actionCounter.increment();

        return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);

        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());

        return new ResponseEntity<TimeEntry>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();

        List<TimeEntry> timeEntryList = timeEntryRepository.list();

        return new ResponseEntity<List<TimeEntry>>(timeEntryList, HttpStatus.OK);

    }


}

