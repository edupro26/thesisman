package pt.ul.fc.css.thesisman.business.entities.defense.location;

/**
 * Represents the url that is going to be used for the defense of the thesis.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
public final class Remote implements Place {

  /** URL for the thesis defense */
  private String url;

  /**
   * Constructs a new Remote.
   *
   * @param url url for defense of the thesis
   */
  public Remote(String url) {
    this.url = url;
  }

  /**
   * Sets the url where the defense is going to take place
   *
   * @param url the url for the defense
   */
  @Override
  public void setPlace(String url) {
    this.url = url;
  }

  /**
   * Returns the url where the defense is going to take place
   *
   * @return the url where the defense is going to take place
   */
  @Override
  public String getPlace() {
    return url;
  }

  /**
   * Returns a string representation of the url.
   *
   * @return a string representation of the url
   */
  @Override
  public String toString() {
    return "Remota[" + "url=" + url + ']';
  }
}
