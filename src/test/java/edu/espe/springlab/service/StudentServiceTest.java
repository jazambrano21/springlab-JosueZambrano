package edu.espe.springlab.service;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.repository.StudentRepository;
import edu.espe.springlab.service.impl.StudentServiceImpl;
import edu.espe.springlab.web.advice.ConflictException;// Asegúrate de tener esta clase
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(StudentServiceImpl.class)
public class StudentServiceTest {

    @Autowired
    private StudentServiceImpl service;

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldNotAllowDuplicateEmail() {
        // Guardamos un estudiante existente con el email duplicado
        Student existing = new Student();
        existing.setFullName("Second User");
        existing.setEmail("test@example.com");
        existing.setBirthDate(LocalDate.of(2001, 5, 15));
        existing.setActive(true);
        repository.save(existing);

        //Josue Zambrano
        // Creamos una solicitud con el mismo email
        StudentRequestData req = new StudentRequestData();
        req.setFullName("Second User Dup");
        req.setEmail("test@example.com");
        req.setBirthDate(LocalDate.of(2001, 5, 15));

        // Verificamos que lance una excepción de conflicto
        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(ConflictException.class);
    }

}
