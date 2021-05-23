package com.skhu.luxuryshop.user.repository;

import com.skhu.luxuryshop.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}