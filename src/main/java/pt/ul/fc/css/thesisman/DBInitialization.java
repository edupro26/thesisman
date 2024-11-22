package pt.ul.fc.css.thesisman;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;
import pt.ul.fc.css.thesisman.business.entities.SchoolYear;
import pt.ul.fc.css.thesisman.business.entities.ThemeApplication;
import pt.ul.fc.css.thesisman.business.entities.defense.location.Room;
import pt.ul.fc.css.thesisman.business.entities.theme.DissertationTheme;
import pt.ul.fc.css.thesisman.business.entities.theme.Theme;
import pt.ul.fc.css.thesisman.business.entities.user.CompanyUser;
import pt.ul.fc.css.thesisman.business.entities.user.Student;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;
import pt.ul.fc.css.thesisman.business.repositories.ApplicationRepository;
import pt.ul.fc.css.thesisman.business.repositories.MastersDegreeRepository;
import pt.ul.fc.css.thesisman.business.repositories.RoomRepository;
import pt.ul.fc.css.thesisman.business.repositories.theme.DissertationThemeRepository;
import pt.ul.fc.css.thesisman.business.repositories.theme.ThemeRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.CompanyUserRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.StudentRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.TeacherRepository;

@Component
public class DBInitialization {

  private final StudentRepository studentRepository;
  private final TeacherRepository teacherRepository;
  private final CompanyUserRepository companyUserRepository;
  private final MastersDegreeRepository mastersDegreeRepository;
  private final DissertationThemeRepository dissertationThemeRepository;
  private final ThemeRepository themeRepository;
  private final ApplicationRepository applicationRepository;
  private final RoomRepository roomRepository;

  @Autowired
  public DBInitialization(
      StudentRepository studentRepository,
      TeacherRepository teacherRepository,
      CompanyUserRepository companyUserRepository,
      MastersDegreeRepository mastersDegreeRepository,
      DissertationThemeRepository dissertationThemeRepository,
      ThemeRepository themeRepository,
      ApplicationRepository applicationRepository,
      RoomRepository roomRepository) {
    this.studentRepository = studentRepository;
    this.teacherRepository = teacherRepository;
    this.companyUserRepository = companyUserRepository;
    this.mastersDegreeRepository = mastersDegreeRepository;
    this.dissertationThemeRepository = dissertationThemeRepository;
    this.themeRepository = themeRepository;
    this.applicationRepository = applicationRepository;
    this.roomRepository = roomRepository;
  }

  /** ------ Students and teachers to mock FCUL authentication system ------* */
  @PostConstruct
  @Order(1)
  private void generateTeachers() {
    List<Teacher> teachers = new ArrayList<>();
    teachers.add(new Teacher("Alcides Fonseca", "amfonseca@ciencias.ulisboa.pt", "password"));
    teachers.add(new Teacher("António Casimiro", "casim@ciencias.ulisboa.pt", "password"));
    teachers.add(new Teacher("Diogo Soares", "dfsoares@ciencias.ulisboa.pt", "password"));
    teachers.add(new Teacher("Pedro Ângelo", "pjangelo@ciencias.ulisboa.pt", "password"));
    teacherRepository.saveAll(teachers);

    List<Room> rooms = new ArrayList<>();
    rooms.add(new Room("Sala 1"));
    rooms.add(new Room("Sala 2"));
    rooms.add(new Room("Sala 3"));
    rooms.add(new Room("Sala 4"));
    rooms.add(new Room("Sala 5"));
    rooms.add(new Room("Sala 6"));
    roomRepository.saveAll(rooms);
  }

  @PostConstruct
  @Order(2)
  @SuppressWarnings("OptionalGetWithoutIsPresent")
  private void generateMastersDegrees() {
    List<MastersDegree> mastersDegrees = new ArrayList<>();
    Teacher admin1 = teacherRepository.findByEmail("amfonseca@ciencias.ulisboa.pt").get();
    Teacher admin2 = teacherRepository.findByEmail("casim@ciencias.ulisboa.pt").get();
    Teacher admin3 = teacherRepository.findByEmail("dfsoares@ciencias.ulisboa.pt").get();
    Teacher admin4 = teacherRepository.findByEmail("pjangelo@ciencias.ulisboa.pt").get();
    mastersDegrees.add(new MastersDegree("MEI", admin1));
    mastersDegrees.add(new MastersDegree("MSI", admin2));
    mastersDegrees.add(new MastersDegree("MF", admin3));
    mastersDegrees.add(new MastersDegree("MB", admin4));
    mastersDegreeRepository.saveAll(mastersDegrees);
  }

  @PostConstruct
  @Order(3)
  @SuppressWarnings("OptionalGetWithoutIsPresent")
  private void generateStudents() {
    MastersDegree mei = mastersDegreeRepository.findByName("MEI").get();
    MastersDegree msi = mastersDegreeRepository.findByName("MSI").get();
    MastersDegree mf = mastersDegreeRepository.findByName("MF").get();
    MastersDegree mb = mastersDegreeRepository.findByName("MB").get();
    List<Student> students = new ArrayList<>();
    students.add(new Student("Eduardo Proença", "fc57551@alunos.fc.ul.pt", "password", mei, 16.2));
    students.add(new Student("Tiago Oliveira", "fc54979@alunos.fc.ul.pt", "password", mei, 18.0));
    students.add(new Student("Manuel Barral", "fc52026@alunos.fc.ul.pt", "password", mei, 14.5));
    students.add(new Student("João Pedro", "fc56783@alunos.fc.ul.pt", "password", msi, 15.7));
    students.add(new Student("João Miguel", "fc56784@alunos.fc.ul.pt", "password", mf, 12.4));
    students.add(new Student("João Luís", "fc56785@alunos.fc.ul.pt", "password", mb, 16.8));
    studentRepository.saveAll(students);
  }

  /** ------------------- Mock data for feature testing purposes -------------------* */
  @PostConstruct
  @Order(4)
  private void generateCompanyUsers() {
    List<CompanyUser> companyUsers = new ArrayList<>();
    companyUsers.add(
        new CompanyUser("Frederico Nunes", "frederico.nunes@sonae.pt", "password", "345167452"));
    companyUsers.add(
        new CompanyUser("Tiago Antunes", "tiago.antunes@nos.pt", "password", "154316515"));
    companyUserRepository.saveAll(companyUsers);
  }

  @PostConstruct
  @Order(5)
  @SuppressWarnings("OptionalGetWithoutIsPresent")
  private void generateDissertationThemes() {
    Teacher internal = teacherRepository.findByEmail("amfonseca@ciencias.ulisboa.pt").get();
    MastersDegree mei = mastersDegreeRepository.findByName("MEI").get();
    List<MastersDegree> degrees = new ArrayList<>();
    degrees.add(mei);
    dissertationThemeRepository.save(
        new DissertationTheme(
            "Tema Teste 1", "Sem decricao", 200, degrees, new SchoolYear("2024/2025"), internal));
    dissertationThemeRepository.save(
        new DissertationTheme(
            "Tema Teste 2", "Sem decricao", 200, degrees, new SchoolYear("2024/2025"), internal));
    dissertationThemeRepository.save(
        new DissertationTheme(
            "Tema Teste 3", "Sem decricao", 200, degrees, new SchoolYear("2024/2025"), internal));
    dissertationThemeRepository.save(
        new DissertationTheme(
            "Tema Teste 4", "Sem decricao", 200, degrees, new SchoolYear("2024/2025"), internal));
    dissertationThemeRepository.save(
        new DissertationTheme(
            "Tema Teste 5", "Sem decricao", 200, degrees, new SchoolYear("2024/2025"), internal));
    dissertationThemeRepository.save(
        new DissertationTheme(
            "Tema Teste 6", "Sem decricao", 200, degrees, new SchoolYear("2024/2025"), internal));
  }

  @PostConstruct
  @Order(6)
  @SuppressWarnings("OptionalGetWithoutIsPresent")
  private void generateThemeApplications() {
    Student student = studentRepository.findByEmail("fc57551@alunos.fc.ul.pt").get();
    Student student1 = studentRepository.findByEmail("fc54979@alunos.fc.ul.pt").get();
    Student student2 = studentRepository.findByEmail("fc52026@alunos.fc.ul.pt").get();
    Student student3 = studentRepository.findByEmail("fc56783@alunos.fc.ul.pt").get();
    Teacher admin = teacherRepository.findByEmail("amfonseca@ciencias.ulisboa.pt").get();
    List<Theme> themes = themeRepository.findAll();
    themes.remove(themes.size() - 1);
    ThemeApplication application = new ThemeApplication(student, admin, themes);
    ThemeApplication application1 = new ThemeApplication(student1, admin, themes);
    ThemeApplication application2 = new ThemeApplication(student2, admin, themes);
    ThemeApplication application3 = new ThemeApplication(student3, admin, themes);
    applicationRepository.save(application);
    applicationRepository.save(application1);
    applicationRepository.save(application2);
    applicationRepository.save(application3);
  }
}
