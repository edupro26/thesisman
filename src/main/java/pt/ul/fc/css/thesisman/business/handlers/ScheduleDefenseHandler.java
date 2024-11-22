package pt.ul.fc.css.thesisman.business.handlers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.dtos.ProposalDTO;
import pt.ul.fc.css.thesisman.business.dtos.RoomDTO;
import pt.ul.fc.css.thesisman.business.dtos.ThesisDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.TeacherDTO;
import pt.ul.fc.css.thesisman.business.entities.defense.Jury;
import pt.ul.fc.css.thesisman.business.entities.defense.ProposalDefense;
import pt.ul.fc.css.thesisman.business.entities.defense.ThesisDefense;
import pt.ul.fc.css.thesisman.business.entities.defense.location.Remote;
import pt.ul.fc.css.thesisman.business.entities.defense.location.Reservation;
import pt.ul.fc.css.thesisman.business.entities.defense.location.Room;
import pt.ul.fc.css.thesisman.business.entities.proposal.Proposal;
import pt.ul.fc.css.thesisman.business.entities.thesis.Thesis;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;
import pt.ul.fc.css.thesisman.business.services.*;

@Component
public class ScheduleDefenseHandler {

  private final SessionHandler sessionHandler;
  private final TDefenseService tDefenseService;

  private final PDefenseService pDefenseService;

  private final ThesisService thesisService;

  private final ProposalService proposalService;

  private final UserService userService;

  private final JuryService juryService;

  private final int THESIS_DURATION = 90;

  private final int PROPOSAL_DURATION = 60;

  private final String RESERVATION_TYPE = "PRESENCIAL";
  private final RoomService roomService;

  @Autowired
  public ScheduleDefenseHandler(
          TDefenseService tDefenseService,
          PDefenseService pDefenseService,
          ThesisService thesisService,
          ProposalService proposalService,
          SessionHandler sessionHandler,
          UserService userService,
          JuryService juryService, RoomService roomService) {
    this.tDefenseService = tDefenseService;
    this.pDefenseService = pDefenseService;
    this.thesisService = thesisService;
    this.proposalService = proposalService;
    this.sessionHandler = sessionHandler;
    this.userService = userService;
    this.juryService = juryService;
    this.roomService = roomService;
  }

  public boolean isAvailable(LocalDateTime startDate, String place, boolean isThesis) {
    LocalDateTime endDate = startDate.plusMinutes(isThesis ? THESIS_DURATION : PROPOSAL_DURATION);
    return isAvailableForThesis(startDate, endDate, place)
        && isAvailableForProposal(startDate, endDate, place);
  }

  public boolean isAvailableForThesis(
      LocalDateTime startDate, LocalDateTime endDate, String place) {
    return tDefenseService
        .findRoomReservations(startDate, endDate, RESERVATION_TYPE, place)
        .isEmpty();
  }

  public boolean isAvailableForProposal(
      LocalDateTime startDate, LocalDateTime endDate, String place) {
    return pDefenseService
        .findRoomReservations(startDate, endDate, RESERVATION_TYPE, place)
        .isEmpty();
  }

  public List<ProposalDTO> getProposals() {
    return proposalService.findAllNotGradedProposalsAndInternal(
        sessionHandler.getCurrentUser().getId());
  }

  public List<ThesisDTO> getTheses() {
    return thesisService.findAllNotGradedThesesAndInternal(sessionHandler.getCurrentUser().getId());
  }

  public List<TeacherDTO> getTeachers() {
    return userService.findAllTeachers();
  }

  public void scheduleThesisDefense(
      Long id,
      LocalDateTime startDate,
      String type,
      String place,
      Long arguerId,
      Long presidentId) {
    Thesis thesis = thesisService.findById(id);
    if (thesis == null) {
      throw new IllegalArgumentException("Thesis not found");
    }

    if (!Objects.equals(thesis.getInternal().getId(), sessionHandler.getCurrentUser().getId())) {
      throw new IllegalArgumentException("User not allowed to schedule defense");
    }

    if (arguerId == null || presidentId == null) {
      throw new IllegalArgumentException("Arguer and president must be selected");
    }

    if (Objects.equals(arguerId, presidentId)) {
      throw new IllegalArgumentException("Arguer and president must be different");
    }

    if (Objects.equals(arguerId, sessionHandler.getCurrentUser().getId())
        || Objects.equals(presidentId, sessionHandler.getCurrentUser().getId())) {
      throw new IllegalArgumentException("User cannot be arguer or president");
    }

    if (startDate.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Date está no passado");
    }

    Optional<Teacher> arguer = userService.findTeacherById(arguerId);
    Optional<Teacher> president = userService.findTeacherById(presidentId);
    Optional<Teacher> internal =
        userService.findTeacherById(sessionHandler.getCurrentUser().getId());

    if (arguer.isEmpty()) {
      throw new IllegalArgumentException("Arguer not found");
    }

    if (president.isEmpty()) {
      throw new IllegalArgumentException("President not found");
    }

    if (internal.isEmpty()) {
      throw new IllegalArgumentException("Internal advisor not found");
    }

    if ( Objects.equals(type, "PRESENCIAL") && roomService.findRoomByName(place).isEmpty()) {
      throw new IllegalArgumentException("Sala não existe");
    }


    if (Objects.equals(type, "REMOTA")) {
      Remote remotePlace = new Remote(place);
      Reservation reservation = new Reservation(startDate, remotePlace);

      Jury jury = juryService.createJury(internal.get(), arguer.get(), president.get());
      tDefenseService.createDefense(reservation, thesis, jury);
    } else {
      if (!isAvailable(startDate, place, true)) {
        throw new IllegalArgumentException("Room not available");
      }
      Jury jury = juryService.createJury(internal.get(), arguer.get(), president.get());
      Room room = new Room(place);
      Reservation reservation = new Reservation(startDate, room);

      tDefenseService.createDefense(reservation, thesis, jury);
    }
  }

  public void scheduleProposalDefense(Long id, LocalDateTime startDate, String type, String place) {
    Proposal proposal = proposalService.findById(id);
    if (proposal == null) {
      throw new IllegalArgumentException("Defense not found");
    }

    if (!Objects.equals(proposal.getInternal().getId(), sessionHandler.getCurrentUser().getId())) {
      throw new IllegalArgumentException("User not allowed to schedule defense");
    }

    if (startDate.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Data está no passado");
    }

    if ( Objects.equals(type, "PRESENCIAL") && roomService.findRoomByName(place).isEmpty()) {
      throw new IllegalArgumentException("Sala não existe");
    }

    if (Objects.equals(type, "REMOTA")) {
      Remote remotePlace = new Remote(place);
      Reservation reservation = new Reservation(startDate, remotePlace);
      pDefenseService.createDefense(reservation, proposal);
    } else {
      if (!isAvailable(startDate, place, false)) {
        throw new IllegalArgumentException("Room not available");
      }

      Room room = new Room(place);
      Reservation reservation = new Reservation(startDate, room);

      pDefenseService.createDefense(reservation, proposal);
    }
  }

  public List<RoomDTO> getRooms() {
    return roomService.findAllRooms();
  }
}
