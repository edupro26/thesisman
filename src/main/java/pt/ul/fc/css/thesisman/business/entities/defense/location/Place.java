package pt.ul.fc.css.thesisman.business.entities.defense.location;
/**
 * Represents a place where the thesis is going to take. This interface defines methods for setting
 * and getting a thesis defense location
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
public interface Place {

  /**
   * Sets the location of the defense.
   *
   * @param location the location of the defense
   */
  void setPlace(String location);

  /**
   * Returns the location of the defense.
   *
   * @return the defense's location
   */
  String getPlace();
}
