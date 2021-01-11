package com.board.service;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;

	@Override
	public boolean registerBoard(BoardDTO params) {
		int queryResult = 0;
		if (params.getIdx() == null) {
			queryResult = boardMapper.insertBoard(params);
			//idx가 null이면 AUTO_INCREMENT
		} else {
			queryResult = boardMapper.updateBoard(params);
		}
		//글번호 AUTO_INCREMENT (PK) 자동증가
		//IDX 포함되있으면 게시글수정
		return (queryResult == 1) ? true : false;
	}	//IDX true,false 반환

	//queryResult insertBoard , UpdateBoard의 실행결과 저장
	//한번 실행할때마다 쿼리를 실행한횟수가 +1씩 저장됨
	//params의 id가 null이면 AUTO_INCREMENT속성에 따라 자동증가
	//ID가ㅓ 포함되있으면 게시글을 수정
	@Override
	public BoardDTO getBoardDetail(Long idx) {
		return boardMapper.selectBoardDetail(idx);
	}
	//게시글 조회

	@Override
	public boolean deleteBoard(Long idx) {
		//게시글 조회
		//상태값이 Y인 경우에는 삭제X,
		int queryResult = 0;
		BoardDTO board = boardMapper.selectBoardDetail(idx);
		if (board != null && "N".equals(board.getDeleteYn())) {
			queryResult = boardMapper.deleteBoard(idx);
		}
		return (queryResult == 1) ? true : false;
	}
	//특정 게시글 조회, 삭제여부(delertYn) 상태값이 Y인경우 삭제 X

	@Override
	public List<BoardDTO> getBoardList() {
		List<BoardDTO> boardList  = Collections.emptyList();

		int boardTtotalCount = boardMapper.selectBoardTotalCount();
		//전체 게시글 조회

		if (boardTtotalCount > 0){
			boardList = boardMapper.selectBoardList();
			//전체 게시글이 1개 이상이면 호출
		}
		return boardList;
	}
	//


}
