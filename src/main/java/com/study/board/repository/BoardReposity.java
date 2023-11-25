package com.study.board.repository;

import com.study.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardReposity extends JpaRepository<Board,Integer> { //엔티티는 Board,PK의 타입은 Integer
    Page<Board> findByTitleContaining(String searchKeyword,Pageable pageable); //제목에 포함된 키워드를 찾는 메서드
}
