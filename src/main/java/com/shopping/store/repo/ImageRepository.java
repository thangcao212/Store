package com.shopping.store.repo;

import com.shopping.store.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}