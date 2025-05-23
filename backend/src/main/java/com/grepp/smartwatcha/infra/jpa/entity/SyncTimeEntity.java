package com.grepp.smartwatcha.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "syncTime")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SyncTimeEntity {
  @Id
  private String type;

  private LocalDateTime syncTime;

  private int newlyAddedCount;

  private int failedCount;

  @Column(nullable = false)
  private int enrichFailedCount;
}
