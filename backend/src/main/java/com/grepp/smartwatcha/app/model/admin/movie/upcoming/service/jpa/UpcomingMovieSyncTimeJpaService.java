package com.grepp.smartwatcha.app.model.admin.movie.upcoming.service.jpa;

import com.grepp.smartwatcha.app.model.admin.movie.upcoming.repository.jpa.UpcomingMovieSyncTimeJpaRepository;
import com.grepp.smartwatcha.infra.jpa.entity.SyncTimeEntity;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpcomingMovieSyncTimeJpaService {
  private final UpcomingMovieSyncTimeJpaRepository upcomingMovieSyncTimeJpaRepository;

  public void update(String type, int newlyAddedCount, int failedCount, int enrichFailed){
    SyncTimeEntity entity = upcomingMovieSyncTimeJpaRepository.findById(type)
        .orElse(SyncTimeEntity.builder().type(type).build());

    entity.setSyncTime(LocalDateTime.now());
    entity.setNewlyAddedCount(newlyAddedCount);
    entity.setFailedCount(failedCount);
    entity.setEnrichFailedCount(enrichFailed);

    upcomingMovieSyncTimeJpaRepository.save(entity);
  }

  public LocalDateTime getLastSyncTime(String type){
    return upcomingMovieSyncTimeJpaRepository.findById(type)
        .map(SyncTimeEntity::getSyncTime)
        .orElse(null);
  }
}
