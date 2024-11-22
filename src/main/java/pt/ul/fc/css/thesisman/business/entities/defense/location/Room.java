package pt.ul.fc.css.thesisman.business.entities.defense.location;

import jakarta.persistence.*;
import pt.ul.fc.css.thesisman.business.dtos.RoomDTO;

/**
 * Represents the room where the thesis is going to be defended.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */

@Entity
@Table(name = "sala")
public final class Room implements Place {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Column(name = "sala_nome", nullable = false, unique = true)
  private String room;

  /**
   * Constructs a new Room.
   *
   * @param room the room where
   */
  public Room(String room) {
    this.room = room;
  }

  public Room() {

  }

  /**
   * Sets the room where the defense is going to take place
   *
   * @param room the location of the room
   */
  @Override
  public void setPlace(String room) {
    this.room = room;
  }

  /**
   * Returns a string representation of the room.
   *
   * @return a string representation of the room
   */
  @Override
  public String getPlace() {
    return room;
  }

  /**
   * Returns a string representation of the room.
   *
   * @return a string representation of the room
   */
  @Override
  public String toString() {
    return "Sala[" + "sala=" + room + ']';
  }

  public RoomDTO toDTO() {
    RoomDTO roomDTO = new RoomDTO();
    roomDTO.setPlace(room);
    return roomDTO;
  }
}
