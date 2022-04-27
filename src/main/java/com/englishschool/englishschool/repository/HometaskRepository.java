package com.englishschool.englishschool.repository;

import com.englishschool.englishschool.entity.HometaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface HometaskRepository extends JpaRepository<HometaskEntity, Long>  {
    List<HometaskEntity> findByIdIn(Collection<Long> ids);
}
