package test.pivotal.pal.tracker;

import io.pivotal.pal.tracker.InMemoryTimeEntryRepository;
import io.pivotal.pal.tracker.TimeEntry;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryTimeEntryRepositoryTest {
    @Test
    public void create() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        long projectId = 123L;
        long userId = 456L;
        long timeEntryId = 1L;
        TimeEntry expected = new TimeEntry(timeEntryId, projectId, userId, LocalDate.parse("2017-01-08"), 8l);


        TimeEntry createdTimeEntry = repo.create(new TimeEntry(projectId, userId, LocalDate.parse("2017-01-08"), 8l));


        assertThat(createdTimeEntry).isEqualTo(expected);

        TimeEntry readEntry = repo.find(createdTimeEntry.getId());

        assertThat(readEntry).isEqualTo(expected);
    }

    @Test
    public void find() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();

        long projectId = 123L;
        long userId = 456L;
        repo.create(new TimeEntry(projectId, userId, LocalDate.parse("2017-01-08"), 8l));

        long timeEntryId = 1L;
        TimeEntry expected = new TimeEntry(timeEntryId, projectId, userId, LocalDate.parse("2017-01-08"), 8l);
        TimeEntry readEntry = repo.find(timeEntryId);
        assertThat(readEntry).isEqualTo(expected);
    }

    @Test
    public void find_MissingEntry() {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();

        long timeEntryId = 1L;

        TimeEntry readEntry = repo.find(timeEntryId);
        assertThat(readEntry).isNull();
    }

    @Test
    public void list() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        repo.create(new TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8l));
        repo.create(new TimeEntry(789L, 654L, LocalDate.parse("2017-01-07"), 4l));

        List<TimeEntry> expected = asList(
                new TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8l),
                new TimeEntry(2L, 789L, 654L, LocalDate.parse("2017-01-07"), 4l)
        );
        assertThat(repo.list()).isEqualTo(expected);
    }

    @Test
    public void update() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        TimeEntry created = repo.create(new TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8l));

        TimeEntry updatedEntry = repo.update(
                created.getId(),
                new TimeEntry(321L, 654L, LocalDate.parse("2017-01-09"), 5l));

        TimeEntry expected = new TimeEntry(created.getId(), 321L, 654L, LocalDate.parse("2017-01-09"), 5l);
        assertThat(updatedEntry).isEqualTo(expected);
        assertThat(repo.find(created.getId())).isEqualTo(expected);
    }

    @Test
    public void update_MissingEntry() {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();

        TimeEntry updatedEntry = repo.update(
                1L,
                new TimeEntry(321L, 654L, LocalDate.parse("2017-01-09"), 5l));

        assertThat(updatedEntry).isNull();
    }

    @Test
    public void delete() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();

        long projectId = 123L;
        long userId = 456L;
        TimeEntry created = repo.create(new TimeEntry(projectId, userId, LocalDate.parse("2017-01-08"), 8l));

        repo.delete(created.getId());
       // assertThat(repo.list()).isEmpty();
    }

    @Test
    public void deleteKeepsTrackOfLatestIdProperly() {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();

        long projectId = 123L;
        long userId = 456L;
        TimeEntry created = repo.create(new TimeEntry(projectId, userId, LocalDate.parse("2017-01-08"), 8l));

        assertThat(created.getId()).isEqualTo(1);

        repo.delete(created.getId());

        TimeEntry createdSecond = repo.create(new TimeEntry(projectId, userId, LocalDate.parse("2017-01-08"), 8l));

        assertThat(createdSecond.getId()).isEqualTo(2);
    }
}
