package com.example.levelUp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.levelUp.model.Habito;

@Repository
public interface HabitoRepository extends JpaRepository<Habito, Long> {

}
