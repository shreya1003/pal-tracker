package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryTimeEntryRepository implements  TimeEntryRepository{

    private Map<Long,TimeEntry> repo = new HashMap<>();

    Long timeEntryId = 1L;

    public TimeEntry create(TimeEntry timeEntry) {

        TimeEntry newEntry = new TimeEntry(timeEntryId, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getParse(), timeEntry.getHours());

        repo.put(timeEntryId , newEntry);

        timeEntryId++;

        return newEntry;
    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {
        TimeEntry updatedTimeEntry = new TimeEntry(timeEntryId, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getParse(), timeEntry.getHours());

        repo.put(timeEntryId, updatedTimeEntry);

        return updatedTimeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return repo.get(id);
    }

    @Override
    public void delete(long timeEntryId) {
        repo.remove(timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {

        List<TimeEntry> listOfEntry=new ArrayList<>();

        repo.entrySet().forEach(entry -> listOfEntry.add(entry.getValue()));

        return listOfEntry;


    }


}
