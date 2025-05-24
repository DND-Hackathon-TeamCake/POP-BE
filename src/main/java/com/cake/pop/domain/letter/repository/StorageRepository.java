package com.cake.pop.domain.letter.repository;

import com.cake.pop.entity.Storage;
import com.cake.pop.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {

    @Query("SELECT s FROM Storage s JOIN FETCH s.letter WHERE s.user = :user")
    List<Storage> findByUser(User user);
}
