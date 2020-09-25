package com.example.chatdemo.repositories;

import com.example.chatdemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param(value = "username") String username);

    @Query(value = "SELECT u FROM User u " +
            "LEFT JOIN FETCH u.joinedChatrooms " +
            "WHERE u.id = :user_id")
    Optional<User> joinFetchFindById(
            @Param(value = "user_id") Long userId);
}
