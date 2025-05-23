package com.grepp.smartwatcha.app.model.admin.tag;

import com.grepp.smartwatcha.infra.jpa.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminTagService {

  private final AdminTagJpaRepository adminTagJpaRepository;

  public Page<TagEntity> findTagsByKeyword(String keyword, Pageable pageable) {
    if  (keyword == null || keyword.isBlank()){
      return adminTagJpaRepository.findAll(pageable);
    } else {
      return adminTagJpaRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }
  }
}
