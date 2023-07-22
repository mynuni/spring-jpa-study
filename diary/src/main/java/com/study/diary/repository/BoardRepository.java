package com.study.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.diary.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}
