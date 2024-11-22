package pt.ul.fc.css.thesisman.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.business.dtos.user.CompanyUserDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.TeacherDTO;
import pt.ul.fc.css.thesisman.business.entities.user.CompanyUser;
import pt.ul.fc.css.thesisman.business.entities.user.Student;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;
import pt.ul.fc.css.thesisman.business.repositories.user.CompanyUserRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.StudentRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.TeacherRepository;

@Service
public class UserService {

  private final StudentRepository studentRepository;
  private final TeacherRepository teacherRepository;
  private final CompanyUserRepository companyUserRepository;

  @Autowired
  public UserService(
      StudentRepository studentRepository,
      TeacherRepository teacherRepository,
      CompanyUserRepository companyUserRepository) {
    this.studentRepository = studentRepository;
    this.teacherRepository = teacherRepository;
    this.companyUserRepository = companyUserRepository;
  }

  public Optional<Teacher> findTeacherById(Long id) {
    return teacherRepository.findById(id);
  }

  public Optional<StudentDTO> findStudentById(long id) {
    Optional<Student> found = studentRepository.findById(id);
    StudentDTO student = null;
    if (found.isPresent()) student = found.get().toDTO();
    return Optional.ofNullable(student);
  }

  public Optional<StudentDTO> findStudentByEmail(String email) {
    Optional<Student> found = studentRepository.findByEmail(email);
    StudentDTO student = null;
    if (found.isPresent()) student = found.get().toDTO();
    return Optional.ofNullable(student);
  }

  public Optional<TeacherDTO> findTeacherByEmail(String email) {
    Optional<Teacher> found = teacherRepository.findByEmail(email);
    TeacherDTO teacher = null;
    if (found.isPresent()) teacher = found.get().toDTO();
    return Optional.ofNullable(teacher);
  }

  public Optional<CompanyUserDTO> findCompanyUserByEmail(String email) {
    Optional<CompanyUser> found = companyUserRepository.findByEmail(email);
    CompanyUserDTO companyUser = null;
    if (found.isPresent()) companyUser = found.get().toDTO();
    return Optional.ofNullable(companyUser);
  }

  public CompanyUserDTO saveCompanyUser(CompanyUser companyUser) {
    return companyUserRepository.save(companyUser).toDTO();
  }

  public List<TeacherDTO> findAllTeachers() {
    List<Teacher> teachers = teacherRepository.findAll();
    List<TeacherDTO> dtos = new ArrayList<>();
    for (Teacher t : teachers) dtos.add(t.toDTO());
    return dtos;
  }

  public List<StudentDTO> findStudentsNotAssociatedWithAnyProposalAndWithTheme(Long themeId) {
    List<Student> students =
        studentRepository.findStudentsNotAssociatedWithAnyProposalAndWithTheme(themeId);
    List<StudentDTO> dtos = new ArrayList<>();
    for (Student s : students) dtos.add(s.toDTO());
    return dtos;
  }
}
