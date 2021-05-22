package com.skhu.luxuryshop.user.repository;

import com.skhu.luxuryshop.user.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
}