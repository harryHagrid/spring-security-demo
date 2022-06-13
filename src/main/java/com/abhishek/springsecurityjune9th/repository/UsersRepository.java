package com.abhishek.springsecurityjune9th.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhishek.springsecurityjune9th.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

}
