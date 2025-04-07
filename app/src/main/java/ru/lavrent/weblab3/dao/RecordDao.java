package ru.lavrent.weblab3.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import ru.lavrent.weblab3.models.Record;
import ru.lavrent.weblab3.util.DBService;

/**
 * Data Access Object (DAO) for {@link Record} entity.
 * <p>
 * Provides static methods for CRUD operations with coordinate records in the database.
 * Uses JPA EntityManager for persistence operations.
 * </p>
 */
public abstract class RecordDao {

  /**
     * Saves a record to the database.
     * <p>
     * Begins transaction, persists the record, and commits.
     * </p>
     * 
     * @param record the {@link Record} to be saved (must not be {@code null})
     * @throws jakarta.persistence.PersistenceException if database operation fails
     * @throws IllegalArgumentException if record is null
     */
  public static void save(Record record) {
    EntityManager em = DBService.getInstance().getEntityManager();
    em.getTransaction().begin();
    em.persist(record);
    em.getTransaction().commit();
  }

  /**
     * Retrieves all records from database sorted by creation date (newest first).
     * 
     * @return List of {@link Record} objects ordered by createdAt DESC
     * @throws jakarta.persistence.PersistenceException if query execution fails
     */
  public static List<Record> getAll() {
    EntityManager em = DBService.getInstance().getEntityManager();
    return em.createQuery("SELECT r FROM Record r ORDER BY r.createdAt DESC", Record.class).getResultList();
  }

  /**
     * Deletes all records from the database.
     * <p>
     * Executes bulk delete operation within a transaction.
     * </p>
     * 
     * @throws jakarta.persistence.PersistenceException if delete operation fails
     */
  public static void deleteAll() {
    EntityManager em = DBService.getInstance().getEntityManager();
    em.getTransaction().begin();
    em.createQuery("DELETE FROM Record").executeUpdate();
    em.getTransaction().commit();
  }
}
