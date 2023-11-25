package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.dsig.SignatureProperty;
import java.io.File;

import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardReposity boardReposity;
    //글 작성 처리
    public void write(Board board,MultipartFile file) throws Exception{
        //파일 업로드 처리 시작
        String projectPath= System.getProperty("user.dir") //경로를 가져옴
        + "\\src\\main\\webapp\\"; //파일이 저장될 폴더의 경로
        UUID uuid=UUID.randomUUID(); //랜덤으로 식별자를 생성
        String fileName=uuid + "_" + file.getOriginalFilename(); //UUID와 파일이름을 포함된 파일 이름으로 저장
        File saveFile=new File(projectPath,fileName); //projectPath 는 위에서 작성한 경로,name은 전달받을 이름
        file.transferTo(saveFile);
        board.setFilepath(fileName);
        board.setFilepath("/webapp/" + fileName); //static 아래부분의 파일 경로로만으로도 접근이 가능
        //파일업로드 처리 끝
        boardReposity.save(board);
    }
    public Page<Board> boardList(Pageable pageable){
        return boardReposity.findAll(pageable);
    }
    public Board boardView(Integer id){
        return boardReposity.findById(id).get();
    }
    //특정 게시글 삭제
    public void boardDlete(Integer id){
        boardReposity.deleteById(id);
    }
    //검색 기능
    public Page<Board> boardSeachList(String searchKeyword,Pageable pageable){
        //검색 기능 추가
        return boardReposity.findByTitleContaining(searchKeyword,pageable);
    }


}
