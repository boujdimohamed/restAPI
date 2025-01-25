package com.example.rest.api.repository;

import com.example.rest.api.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTagById(Long id);
    Tag findTagByLabel(String label);
    //alle Tags finden with query
    @Query("SELECT DISTINCT t FROM Tag t")
    List<Tag> AllTags();
}
