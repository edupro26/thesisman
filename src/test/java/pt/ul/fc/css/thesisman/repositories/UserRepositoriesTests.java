package pt.ul.fc.css.thesisman.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;
import pt.ul.fc.css.thesisman.business.entities.user.CompanyUser;
import pt.ul.fc.css.thesisman.business.entities.user.Student;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;
import pt.ul.fc.css.thesisman.business.entities.user.User;
import pt.ul.fc.css.thesisman.business.repositories.MastersDegreeRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.CompanyUserRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.StudentRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.TeacherRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.UserRepository;

@DataJpaTest
public class UserRepositoriesTests {

  @Autowired private UserRepository userRepository;

  @Autowired private StudentRepository studentRepository;

  @Autowired private TeacherRepository teacherRepository;

  @Autowired private CompanyUserRepository companyUserRepository;

  @Autowired private MastersDegreeRepository mastersRepository;

  private final List<Long> ids = new ArrayList<>();

  @BeforeEach
  void setup() {
    Teacher teacher = new Teacher("Teacher1", "t1@email.com", "password");
    teacherRepository.save(teacher);

    MastersDegree mastersDegree = new MastersDegree("LEI", teacher);
    mastersRepository.save(mastersDegree);

    ids.add(
        studentRepository
            .save(new Student("Aluno1", "aluno1@alunos.fc.ul.pt", "password", mastersDegree, 14.0))
            .getId());
    ids.add(
        studentRepository
            .save(new Student("Aluno2", "aluno2@alunos.fc.ul.pt", "password", mastersDegree, 15.0))
            .getId());
    ids.add(
        teacherRepository
            .save(new Teacher("Professor1", "professor1@ciencias.ulisboa.pt", "password"))
            .getId());
    ids.add(
        teacherRepository
            .save(new Teacher("Professor2", "professor2@ciencias.ulisboa.pt", "password"))
            .getId());
    ids.add(
        companyUserRepository
            .save(
                new CompanyUser("Empresarial1", "empresarial1@empresa.pt", "password", "345135162"))
            .getId());
    ids.add(
        companyUserRepository
            .save(
                new CompanyUser("Empresarial2", "empresarial2@empresa.pt", "password", "524123612"))
            .getId());
  }

  @Test
  void shouldFindUserById() {
    Optional<User> user = userRepository.findById(ids.get(1));
    assertTrue(user.isPresent());
  }

  @Test
  void shouldFindStundentById() {
    Optional<Student> student = studentRepository.findById(ids.get(1));
    assertTrue(student.isPresent());
  }

  @Test
  void shouldFindTeacherById() {
    Optional<Teacher> teacher = teacherRepository.findById(ids.get(3));
    assertTrue(teacher.isPresent());
  }

  @Test
  void shouldFindCompanyUserById() {
    Optional<CompanyUser> companyUser = companyUserRepository.findById(ids.get(5));
    assertTrue(companyUser.isPresent());
  }

  @Test
  void shouldFindStudentByEmail() {
    String email = "aluno1@alunos.fc.ul.pt";
    Optional<Student> student = studentRepository.findByEmail(email);
    assertTrue(student.isPresent());
  }

  @Test
  void shouldFindTeacherByEmail() {
    String email = "professor1@ciencias.ulisboa.pt";
    Optional<Teacher> teacher = teacherRepository.findByEmail(email);
    assertTrue(teacher.isPresent());
  }

  @Test
  void shouldFindCompanyUserByEmail() {
    String email = "empresarial1@empresa.pt";
    Optional<CompanyUser> companyUser = companyUserRepository.findByEmail(email);
    assertTrue(companyUser.isPresent());
  }

  @Test
  void shouldFindAllUsers() {
    List<User> users = userRepository.findAll();
    assertEquals(7, users.size());
  }

  @Test
  void shouldFindAllStudents() {
    List<Student> students = studentRepository.findAll();
    assertEquals(2, students.size());
  }

  @Test
  void shouldFindAllTeachers() {
    List<Teacher> teachers = teacherRepository.findAll();
    assertEquals(3, teachers.size());
  }

  @Test
  void shouldFindAllCompanyUsers() {
    List<CompanyUser> companyUsers = companyUserRepository.findAll();
    assertEquals(2, companyUsers.size());
  }

  @Test
  void shouldSaveCompanyUser() {
    companyUserRepository.save(
        new CompanyUser("Empresarial3", "empresarial3@empresa.pt", "password", "138741462"));
    List<CompanyUser> companyUsers = companyUserRepository.findAll();
    assertEquals(3, companyUsers.size());
  }

  @Test
  void shouldNotFindNonExistentUser() {
    Long id = 999L;
    Optional<User> user = userRepository.findById(id);
    assertTrue(user.isEmpty());
  }
}
