package sg.edu.nus.iss.d13revision.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import sg.edu.nus.iss.d13revision.models.Person;

public class PersonServiceTest {

    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonService();
    }

    @Test
    void testGetPersons_initialData() {
        List<Person> persons = personService.getPersons();

        assertNotNull(persons);
        assertEquals(2, persons.size());
        assertEquals("Mark", persons.get(0).getFirstName());
        assertEquals("Zuckerberg", persons.get(0).getLastName());
        assertEquals("Elon", persons.get(1).getFirstName());
        assertEquals("Musk", persons.get(1).getLastName());
    }

    @Test
    void testAddPerson() {
        Person newPerson = new Person("John", "Doe");

        personService.addPerson(newPerson);

        List<Person> persons = personService.getPersons();
        assertEquals(3, persons.size());

        Person addedPerson = persons.stream()
                .filter(p -> "John".equals(p.getFirstName()) && "Doe".equals(p.getLastName()))
                .findFirst()
                .orElse(null);

        assertNotNull(addedPerson);
        assertNotNull(addedPerson.getId());
    }

    @Test
    void testUpdatePerson() {
        List<Person> persons = personService.getPersons();
        Person existingPerson = persons.get(0);
        String originalId = existingPerson.getId();

        existingPerson.setFirstName("Markus");
        existingPerson.setLastName("Zuckerman");

        personService.updatePerson(existingPerson);

        List<Person> updatedPersons = personService.getPersons();
        assertEquals(2, updatedPersons.size());

        Person updated = updatedPersons.stream()
                .filter(p -> originalId.equals(p.getId()))
                .findFirst()
                .orElse(null);

        assertNotNull(updated);
        assertEquals("Markus", updated.getFirstName());
        assertEquals("Zuckerman", updated.getLastName());
    }

    @Test
    void testRemovePerson() {
        List<Person> persons = personService.getPersons();
        int initialSize = persons.size();
        Person personToRemove = persons.get(0);
        String removedId = personToRemove.getId();

        personService.removePerson(personToRemove);

        List<Person> remainingPersons = personService.getPersons();
        assertEquals(initialSize - 1, remainingPersons.size());

        Person removed = remainingPersons.stream()
                .filter(p -> removedId.equals(p.getId()))
                .findFirst()
                .orElse(null);

        assertNull(removed);
    }

}