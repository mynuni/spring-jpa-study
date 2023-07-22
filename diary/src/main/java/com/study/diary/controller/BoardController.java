package com.study.diary.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.diary.dto.BoardDto;
import com.study.diary.service.BoardService;

import lombok.RequiredArgsConstructor;

/**
 * 	추가할 것
 * 	1. Pageable
 * 	2. ResponseEntity 상태코드 같이 반환
 * 	3. 회원:게시글 또는 게시글:댓글 1:N 관계 만들어 보기
 *  4. Service 클래스 나머지 Builder 메서드로 추출하기
 * 	5. Request/Response DTO 분리해보기
 * 
 *  복습할 것
 * 	1. UPDATE부분 @Transactional
 *  2. 영속성 컨텐스트
 *  3. Entity 설계 시 권장 사항
 */

@RequestMapping("/boards")
@RequiredArgsConstructor
@RestController
public class BoardController {

	private final BoardService boardService;
	
	// 목록
	@GetMapping
	public List<BoardDto> findAll() {
		return boardService.findAll();
	}

	// 조회
	@GetMapping("/{id}")
	public BoardDto findById(@PathVariable Long id) {
		return boardService.findById(id);
	}

	// 등록
	@PostMapping
	public BoardDto createBoard(@RequestBody BoardDto boardDto) {
		return boardService.createBoard(boardDto);
	}

	// 수정
	@PutMapping("/{id}")
	public BoardDto updateBoard(@PathVariable Long id, @RequestBody BoardDto boardDto) {
		return boardService.updateBoard(id, boardDto);
	}

	// 삭제
	@DeleteMapping("/{id}")
	public void deleteBoard(@PathVariable Long id) {
		boardService.deleteBoard(id);
	}

}