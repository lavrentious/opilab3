package ru.lavrent.weblab3.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.lavrent.weblab3.dao.RecordDao;
import ru.lavrent.weblab3.models.Record;
import ru.lavrent.weblab3.util.Validator;

@Named("formBean")
@ApplicationScoped

/**
 * Managed bean for handling form submissions and coordinate validation.
 * <p>
 * This bean is responsible for processing user input (X, Y, R coordinates),
 * validating them, and saving records to the database. It also provides
 * predefined values for X and R dropdowns in the UI.
 * </p>
 * @ApplicationScoped Bean lifecycle matches the application scope.
 * @Named("formBean") Exposes the bean to JSF EL expressions.
 * @author mr. Lavrent
 */
public class FormBean implements Serializable {
  @Inject
  private HistoryBean historyBean;

  /**
     * Gets the associated HistoryBean instance.
     * 
     * @return the HistoryBean used for managing history records.
  */
  public HistoryBean getHistoryBean() {
    return historyBean;
  }

  public void setHistoryBean(HistoryBean historyBean) {
    this.historyBean = historyBean;
  }

  private Float x;
  private String y;
  private Float r;

  private String hiddenX;
  private String hiddenY;

  /**
     * Gets the hidden X coordinate (used for alternative input methods).
     * 
     * @return the hidden X coordinate as a String.
     */
  public String getHiddenX() {
    return hiddenX;
  }

  /**
     * Gets the hidden Y coordinate (used for alternative input methods).
     * 
     * @return the hidden Y coordinate as a String.
     */
  public String getHiddenY() {
    return hiddenY;
  }

  /**
     * Sets the hidden X coordinate.
     * 
     * @param hiddenX the X coordinate to set (as String).
     */
  public void setHiddenX(String hiddenX) {
    System.out.println("HIDDEN X: " + hiddenX);
    this.hiddenX = hiddenX;
  }

  /**
     * Sets the hidden Y coordinate.
     * 
     * @param hiddenY the Y coordinate to set (as String).
     */
  public void setHiddenY(String hiddenY) {
    System.out.println("HIDDEN Y: " + hiddenY);
    this.hiddenY = hiddenY;
  }

  /**
     * Provides predefined values for X coordinate dropdown.
     * 
     * @return List of valid X values (-4 to 4).
     */
  public List<Float> getXValues() {
    return Arrays.asList(-4f, -3f, -2f, -1f, 0f, 1f, 2f, 3f, 4f);
  }

  /**
     * Provides predefined values for R parameter dropdown.
     * 
     * @return List of valid R values (1 to 3 with 0.5 step).
     */
  public List<Float> getRValues() {
    return Arrays.asList(1f, 1.5f, 2f, 2.5f, 3f);
  }

  /**
     * Processes form submission with visible coordinates.
     * <p>
     * Validates input, creates a new Record, saves it via DAO,
     * and updates the history.
     * </p>
     * 
     * @throws IllegalArgumentException if validation fails.
     */
  public String submit() {
    System.out.println("Adding record");
    System.out.println("x: " + this.getX() + ", y: " + this.getY() + ", r: " + this.getR());
    Validator.validate(this.getX(), this.getY(), this.getR());
    Record record = new Record(this.getX(), Float.parseFloat(this.getY()), this.getR(), new Date());
    RecordDao.save(record);
    this.setX(null);
    this.setY(null);
    historyBean.updateLocal();

    // UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
    // return view.getViewId() + "?faces-redirect=true";
    return null;
  }

  /**
     * Processes form submission with hidden coordinates.
     * <p>
     * Alternative submission method that bypasses visible UI elements.
     * </p>
     */
  public String submitHidden() {
    System.out.println("HIDDEN Adding record");
    System.out.println("x: " + this.getHiddenX() + ", y: " + this.getHiddenY() + ", r: " + this.getR());
    Record record = new Record(Float.parseFloat(this.getHiddenX()), Float.parseFloat(this.getHiddenY()),
        this.getR(), new Date());
    RecordDao.save(record);
    this.setX(null);
    this.setY(null);
    historyBean.updateLocal();

    // UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
    // return view.getViewId() + "?faces-redirect=true";
    return null;
  }

  /**
     * Gets the current X coordinate value.
     * 
     * @return the X coordinate as Float.
     */
  public Float getX() {
    return x;
  }

  /**
     * Sets the X coordinate value.
     * 
     * @param x the X coordinate to set.
     */
  public void setX(Float x) {
    this.x = x;
  }

  /**
     * Gets the current Y coordinate value (as String for input flexibility).
     * 
     * @return the Y coordinate as String.
     */
  public String getY() {
    return y;
  }

  /**
     * Sets the Y coordinate value.
     * 
     * @param y the Y coordinate to set (as String).
     */
  public void setY(String y) {
    this.y = y;
  }

  /**
     * Gets the current R parameter value.
     * 
     * @return the R parameter as Float.
     */
  public Float getR() {
    return r;
  }

  /**
     * Sets the R parameter value.
     * 
     * @param r the R parameter to set.
     */
  public void setR(Float r) {
    this.r = r;
  }
}
