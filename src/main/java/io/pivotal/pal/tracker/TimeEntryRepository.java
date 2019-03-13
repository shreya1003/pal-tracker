package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.List;


public interface TimeEntryRepository {

    public TimeEntry create(TimeEntry timeEntry);

    public TimeEntry update(long timeEntryId, TimeEntry timeEntry);

    public TimeEntry find(long timeEntryId);

    public void delete(long timeEntry);

    List<TimeEntry> list();
}
