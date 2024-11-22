package pt.ul.fc.css.thesisman.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.business.dtos.theme.DissertationThemeDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ProjectThemeDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeSubmissionDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.UserDTO;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;
import pt.ul.fc.css.thesisman.business.entities.SchoolYear;
import pt.ul.fc.css.thesisman.business.entities.theme.DissertationTheme;
import pt.ul.fc.css.thesisman.business.entities.theme.ProjectTheme;
import pt.ul.fc.css.thesisman.business.entities.theme.Theme;
import pt.ul.fc.css.thesisman.business.entities.user.CompanyUser;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;
import pt.ul.fc.css.thesisman.business.repositories.MastersDegreeRepository;
import pt.ul.fc.css.thesisman.business.repositories.theme.DissertationThemeRepository;
import pt.ul.fc.css.thesisman.business.repositories.theme.ProjectThemeRepository;
import pt.ul.fc.css.thesisman.business.repositories.theme.ThemeRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.CompanyUserRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.TeacherRepository;

@Service
public class ThemeService {

  private final ThemeRepository themeRepository;
  private final ProjectThemeRepository projectThemeRepository;
  private final DissertationThemeRepository dissertationThemeRepository;
  private final TeacherRepository teacherRepository;
  private final CompanyUserRepository companyUserRepository;
  private final MastersDegreeRepository mastersDegreeRepository;

  @Autowired
  public ThemeService(
      ThemeRepository themeRepository,
      ProjectThemeRepository projectThemeRepository,
      DissertationThemeRepository dissertationThemeRepository,
      TeacherRepository teacherRepository,
      CompanyUserRepository companyUserRepository,
      MastersDegreeRepository mastersDegreeRepository) {
    this.themeRepository = themeRepository;
    this.projectThemeRepository = projectThemeRepository;
    this.dissertationThemeRepository = dissertationThemeRepository;
    this.teacherRepository = teacherRepository;
    this.companyUserRepository = companyUserRepository;
    this.mastersDegreeRepository = mastersDegreeRepository;
  }

  public Optional<ThemeDTO> findById(Long id) {
    Optional<Theme> theme = themeRepository.findById(id);
    ThemeDTO themeDTO = null;
    if (theme.isPresent()) themeDTO = theme.get().toDTO();
    return Optional.ofNullable(themeDTO);
  }

  public Optional<ThemeDTO> findByTitleAndYear(String title, SchoolYear year) {
    Optional<Theme> theme = themeRepository.findByTitleAndYear(title, year);
    ThemeDTO themeDTO = null;
    if (theme.isPresent()) themeDTO = theme.get().toDTO();
    return Optional.ofNullable(themeDTO);
  }

  public List<ThemeDTO> findByMastersAndYear(String masters, String year) {
    List<Theme> themes = themeRepository.findByMastersAndYear(masters, year);
    List<ThemeDTO> themesDTO = new ArrayList<>();
    for (Theme t : themes) themesDTO.add(t.toDTO());
    return themesDTO;
  }

  public List<ThemeDTO> findByMastersAndSchoolYearWithoutProposals(
      MastersDegree mastersDegree, SchoolYear schoolYear) {
    List<Theme> themes =
        themeRepository.findThemesWithoutProposalsAndWithMastersDegreeAndSchoolYear(
            schoolYear, mastersDegree);
    List<ThemeDTO> themesDTO = new ArrayList<>();
    for (Theme t : themes) themesDTO.add(t.toDTO());
    return themesDTO;
  }

  public List<ThemeDTO> findAll() {
    List<Theme> themes = themeRepository.findAll();
    List<ThemeDTO> themesDTO = new ArrayList<>();
    for (Theme t : themes) themesDTO.add(t.toDTO());
    return themesDTO;
  }

  public List<ProjectThemeDTO> findAllProjectThemes() {
    List<ProjectTheme> themes = projectThemeRepository.findAll();
    List<ProjectThemeDTO> themesDTO = new ArrayList<>();
    for (ProjectTheme t : themes) themesDTO.add(t.toDTO());
    return themesDTO;
  }

  public List<DissertationThemeDTO> findAllDissertationThemes() {
    List<DissertationTheme> themes = dissertationThemeRepository.findAll();
    List<DissertationThemeDTO> themesDTO = new ArrayList<>();
    for (DissertationTheme t : themes) themesDTO.add(t.toDTO());
    return themesDTO;
  }

  public void saveDissertationTheme(
      ThemeSubmissionDTO submission, SchoolYear schoolYear, UserDTO submitter) {
    List<MastersDegree> masters = new ArrayList<>();
    for (Long master : submission.getSelectedMasters()) {
      Optional<MastersDegree> m = mastersDegreeRepository.findById(master);
      m.ifPresent(masters::add);
    }
    Optional<Teacher> internal = teacherRepository.findById(submitter.getId());
    internal.ifPresent(
        teacher ->
            dissertationThemeRepository.save(
                new DissertationTheme(
                    submission.getTitle(),
                    submission.getDescription(),
                    submission.getRemuneration(),
                    masters,
                    schoolYear,
                    teacher)));
  }

  public void saveProjectTheme(
      ThemeSubmissionDTO submission, SchoolYear schoolYear, UserDTO submitter) {
    List<MastersDegree> masters = new ArrayList<>();
    for (Long id : submission.getSelectedMasters()) {
      Optional<MastersDegree> m = mastersDegreeRepository.findById(id);
      m.ifPresent(masters::add);
    }
    Optional<Teacher> internal = teacherRepository.findById(submission.getInternal());
    Optional<CompanyUser> external = companyUserRepository.findById(submitter.getId());
    if (internal.isPresent() && external.isPresent()) {
      projectThemeRepository.save(
          new ProjectTheme(
              submission.getTitle(),
              submission.getDescription(),
              submission.getRemuneration(),
              masters,
              schoolYear,
              internal.get(),
              external.get()));
    }
  }

  public List<ProjectThemeDTO> findNotAssignedProjectThemes() {
    List<ProjectTheme> pts = projectThemeRepository.findProjectThemesNotAssociatedWithAnyProposal();
    List<ProjectThemeDTO> ptDTOs = new ArrayList<>();
    for (ProjectTheme pt : pts) ptDTOs.add(pt.toDTO());
    return ptDTOs;
  }

  public List<DissertationThemeDTO> findNotAssignedDissertationThemes() {
    List<DissertationTheme> dts =
        dissertationThemeRepository.findDissertationThemesNotAssociatedWithAnyProposal();
    List<DissertationThemeDTO> dtDTOs = new ArrayList<>();
    for (DissertationTheme dt : dts) dtDTOs.add(dt.toDTO());
    return dtDTOs;
  }

  public List<ThemeDTO> findByYear(SchoolYear year) {
    List<Theme> themes = themeRepository.findByYear(year);
    List<ThemeDTO> themesDTO = new ArrayList<>();
    for (Theme t : themes) themesDTO.add(t.toDTO());
    return themesDTO;
  }
}
