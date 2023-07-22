package com.study.diary.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.diary.domain.Board;
import com.study.diary.dto.BoardDto;
import com.study.diary.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	// 목록
	@Transactional(readOnly = true)
	public List<BoardDto> findAll() {
		return boardRepository.findAll()
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());

	}

	// 조회
	@Transactional(readOnly = true)
	public BoardDto findById(Long id) {
		// update, delete 부분 중복 코드 발생
		// findById를 메서드로 추출해서 board 반환하도록 하면 좋을 것 같음
		// 반환을 dto말고 Optional로 해주면 Controller에서 바로 상태코드 내려 수 있을 것 같음
		Board board = boardRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id: " + id));
		
		return convertToDto(board);

	}

	// 등록
	@Transactional
	public BoardDto createBoard(BoardDto boardDto) {
		Board board = convertToEntity(boardDto);
		log.info("convert된 entity의 id는 null이어야 함: {}", board.getId() == null);

		Board savedBoard = boardRepository.save(board);
		log.info("save된 entity의 id는 null이 아니어야 함: {}", savedBoard.getId() != null);
		
		return convertToDto(board);
		
	}

	// 수정
	@Transactional
	public BoardDto updateBoard(Long id, BoardDto boardDto) {
		Board foundBoard = boardRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id: " + id));
		
		log.info("업데이트 전");
		log.info("업데이트 대상 id:{}", id);
		log.info("제목:{}", foundBoard.getTitle());
		log.info("내용:{}", foundBoard.getContent());
		log.info("작성자:{}", foundBoard.getAuthor());
		
		foundBoard.update(boardDto.getTitle(), boardDto.getContent(), boardDto.getAuthor());
		
		log.info("업데이트 후");
		log.info("제목:{}", foundBoard.getTitle());
		log.info("내용:{}", foundBoard.getContent());
		log.info("작성자:{}", foundBoard.getAuthor());
		
		return convertToDto(foundBoard);

	}

	// 삭제
	@Transactional
	public void deleteBoard(Long id) {
		Board foundBoard = boardRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id: " + id));
		boardRepository.delete(foundBoard);
		
	}
	
	private BoardDto convertToDto(Board board) {
		return BoardDto.builder()
				.id(board.getId())
				.title(board.getTitle())
				.content(board.getContent())
				.author(board.getAuthor())
				.build();

	}
	
	private Board convertToEntity(BoardDto boardDto) {
		return Board.builder()
				.title(boardDto.getTitle())
				.content(boardDto.getContent())
				.author(boardDto.getAuthor())
				.build();
		
	}

}
