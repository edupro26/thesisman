package pt.ul.fc.css.thesisman.business.entities.defense.location;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;

/**
 * Represents the reservation needed for the defense.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Embeddable
public class Reservation {

  /** Destinguishes the type of reservation as presencial */
  private static final String ROOM = "PRESENCIAL";
  /** Destinguishes the type of reservation as remota */
  private static final String REMOTE = "REMOTA";

  /** The reservation's date. */
  @Column(name = "data", nullable = false)
  private LocalDateTime date;

  /** The reservation's place. */
  @Column(name = "local", nullable = false)
  private String place;

  /** The reservation's type. */
  @Column(name = "tipo", nullable = false)
  private String type;

  /**
   * Constructs a new Reservation.
   *
   * @param date the reservation's date
   * @param room the reservation's location
   */
  public Reservation(LocalDateTime date, Room room) {
    this.date = date;
    this.place = room.getPlace();
    this.type = ROOM;
  }

  /**
   * Constructs a new Reservation.
   *
   * @param date the reservation's date
   * @param remote the reservation's remote link
   */
  public Reservation(LocalDateTime date, Remote remote) {
    this.date = date;
    this.place = remote.getPlace();
    this.type = REMOTE;
  }

  /** No-arg constructor */
  protected Reservation() {}

  /**
   * Sets the reservation's date.
   *
   * @param date the date of the reservation
   */
  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  /**
   * Sets the reservation's location.
   *
   * @param place the location of the reservation
   */
  public void setPlace(String place) {
    this.place = place;
  }

  /**
   * Sets the reservation's type.
   *
   * @param type the reservation type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Returns the reservation's date.
   *
   * @return the date of the reservation
   */
  public LocalDateTime getDate() {
    return date;
  }

  /**
   * Returns the reservation's location.
   *
   * @return the location of the reservation
   */
  public String getPlace() {
    return place;
  }

  /**
   * Returns the reservation's type.
   *
   * @return the type of the reservation
   */
  public String getType() {
    return type;
  }

  /**
   * Returns a string representation of the reservation.
   *
   * @return a string representation of the reservation
   */
  @Override
  public String toString() {
    if (type.equals(ROOM))
      return "Local[" + "data=" + date + ", " + "sala=" + place + ", " + "tipo=" + type + ']';
    else return "Local[" + "data=" + date + ", " + "url=" + place + ", " + "tipo=" + type + ']';
  }
}
