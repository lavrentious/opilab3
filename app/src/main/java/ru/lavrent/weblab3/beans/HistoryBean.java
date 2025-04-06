package ru.lavrent.weblab3.beans;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import ru.lavrent.weblab3.dao.RecordDao;
import ru.lavrent.weblab3.models.Record;

@Named("historyBean")
@ApplicationScoped

/**
 * Managed bean for managing and displaying history of coordinate checks.
 * <p>
 * This bean handles storage and serialization of {@link Record} objects.
 * It provides functionality to clear history and convert records to JSON format.
 * </p>
 * 
 * @ApplicationScoped Bean lifecycle matches the application scope.
 * @Named("historyBean") Exposes the bean to JSF EL expressions.
 * @author mr. Lavrent
 */
public class HistoryBean implements Serializable {

  private List<Record> records;

  /**
     * Initializes the bean by loading records from the database.
     * Automatically called after bean construction.
     */
  @PostConstruct
  public void initialize() {
    updateLocal();
  }

  /**
     * Updates the local records cache by fetching all records from the database.
     */
  public void updateLocal() {
    System.out.println("Updating local");
    records = RecordDao.getAll();
    for (Record record : records) {
      System.out.println("x: " + record.getX() + ", y: " + record.getY() + ", r: " + record.getR() + ", result: "
          + record.getHitString());
    }
  }

  /**
     * Gets the current list of records.
     * 
     * @return Unmodifiable list of {@link Record} objects.
     */
  public List<Record> getRecords() {
    return records;
  }

  /**
     * Serializes records to JSON format using Gson.
     * 
     * @return JSON string representation of records.
     */
  public String getRecordsJson() {
    Gson gson = new Gson();
    return gson.toJson(records);
  }

  /**
     * Clears all records from both database and local cache.
     */
  public String clearHistory() {
    System.out.println("Clearing history");
    RecordDao.deleteAll();
    updateLocal();
    return null;
  }

  /**
     * Gets the system's default time zone ID.
     * 
     * @return String representation of the system time zone (e.g., "Europe/Moscow").
     */
  public String getTimeZone() {
    return java.time.ZoneId.systemDefault().toString();
  }
}
