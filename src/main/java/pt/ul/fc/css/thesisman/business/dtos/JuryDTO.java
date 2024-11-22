package pt.ul.fc.css.thesisman.business.dtos;

import pt.ul.fc.css.thesisman.business.dtos.user.TeacherDTO;

public class JuryDTO {

    private long id;
    private TeacherDTO internalAdvisor;
    private TeacherDTO arguer;
    private TeacherDTO president;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TeacherDTO getTeacher() {
        return internalAdvisor;
    }

    public void setTeacher(TeacherDTO internalAdvisor) {
        this.internalAdvisor = internalAdvisor;
    }

    public TeacherDTO getArguer() {
        return arguer;
    }

    public void setArguer(TeacherDTO arguer) {
        this.arguer = arguer;
    }

    public TeacherDTO getPresident() {
        return president;
    }

    public void setPresident(TeacherDTO president) {
        this.president = president;
    }
}
