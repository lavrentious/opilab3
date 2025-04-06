package ru.lavrent.weblab3.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "records")
public class Record implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "created_at", nullable = false)
  private Date createdAt;

  @Column(name = "x", nullable = false)
  private float x;

  @Column(name = "y", nullable = false)
  private float y;

  @Column(name = "r", nullable = false)
  private float r;

  @Column(name = "is_hit", nullable = false)
  private boolean isHit;

  public Record() {
  }

  public Record(float x, float y, float r, Date createdAt) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.createdAt = createdAt;
    this.isHit = Record.checkHit(x, y, r);
  }

  public String getHitString() {
    return isHit ? "✅" : "❌";
  }

  public static boolean checkHit(float x, float y, float r) {
    if (x >= 0 && y <= 0) {
      return Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2);
    }
    if (x <= 0 && y <= 0) {
      return x >= -r && y >= -r / 2;
    }
    if (x >= 0 && y >= 0) {
      return y <= -2 * x + r;
    }
    return false;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public float getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public float getY() {
    return y;
  }

  public void setY(float y) {
    this.y = y;
  }

  public float getR() {
    return r;
  }

  public void setR(float r) {
    this.r = r;
  }

  public boolean isHit() {
    return isHit;
  }

  public void setHit(boolean isHit) {
    this.isHit = isHit;
  }
}