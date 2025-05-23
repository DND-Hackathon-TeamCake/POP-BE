package com.cake.pop.domain.user.repository;

import com.cake.pop.domain.user.exception.UserErrorCode;
import com.cake.pop.entity.User;
import com.cake.pop.global.exception.RestApiException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    default User getById(Long id){
        return findById(id).orElseThrow(()->new RestApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
