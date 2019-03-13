package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.Objects;

public class TimeEntry {


    private Long hours;
    private Long timeEntryId;
    private Long projectId;
    private Long userId;
    private LocalDate parse;

    public TimeEntry(Long timeEntryId, Long projectId, Long userId, LocalDate parse, Long hours) {
        this.timeEntryId = timeEntryId;
        this.projectId = projectId;
        this.userId = userId;
        this.parse = parse;
        this.hours = hours;
    }

    public TimeEntry(Long projectId, Long userId, LocalDate parse, Long hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.parse = parse;
        this.hours = hours;
    }

    public TimeEntry() {

    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getHours() {
        return hours;
    }

    public LocalDate getParse() {
        return parse;
    }

    public Long getId() {

        return timeEntryId;
    }

    public Long getTimeEntryId() {

        return timeEntryId;
    }


    @Override
    public String toString() {
        return "TimeEntry{" +
                "hours=" + hours +
                ", timeEntryId=" + timeEntryId +
                ", projectId=" + projectId +
                ", userId=" + userId +
                ", parse=" + parse +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeEntry timeEntry = (TimeEntry) o;
        return Objects.equals(hours, timeEntry.hours) &&
                Objects.equals(timeEntryId, timeEntry.timeEntryId) &&
                Objects.equals(projectId, timeEntry.projectId) &&
                Objects.equals(userId, timeEntry.userId) &&
                Objects.equals(parse, timeEntry.parse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, timeEntryId, projectId, userId, parse);
    }
}
