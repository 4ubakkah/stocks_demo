package com.iconiq.demo.persistance;

import java.util.Optional;

import com.iconiq.demo.model.entity.StockUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<StockUser, Long> {

    Optional<StockUser> getByUserName(String userName);
}
