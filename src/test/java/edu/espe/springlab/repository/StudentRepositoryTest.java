package edu.espe.springlab.repository;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository repository;

    @Test
    void shouldSaveAndFindStudentByEmail() {
        Student s = new Student(); // ← corrección
        s.setFullName("Test User");
        s.setEmail("test@example.com");
        s.setBirthDate(LocalDate.of(2000, 10, 10));
        s.setActive(true);

        repository.save(s);

        //Josue Zambrano

        Student s2 = new Student();
        s2.setFullName("Second User");
        s2.setEmail("test@example.com");
        s2.setBirthDate(LocalDate.of(2001, 5, 15));
        s2.setActive(true);

        // Verificar que solo existe un registro con ese email
        long countBefore = repository.count();
        assertThat(countBefore).isEqualTo(1);

        // Confirmar que findByEmail retorna el primer estudiante
        var result = repository.findByEmail("test@example.com");
        assertThat(result).isPresent();
        assertThat(result.get().getFullName()).isEqualTo("Test User");

    }
}

