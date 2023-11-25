package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class BoardController {


    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8090/board/write
    public String boardWriteForm() {

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board,Model model,MultipartFile file)throws Exception{
        boardService.write(board, file);

        model.addAttribute("message","글 작성이 완료!");
        model.addAttribute("searchUrl","/board/list");

        return "message";
    }
    @GetMapping("/board/list")
    public String boardList(Model model,@PageableDefault(page = 0,size = 10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,String searchKeyword){
        Page<Board> list=null;
        if (searchKeyword==null){
            list = boardService.boardList(pageable); //검색할 키워드가 들어오지 않는 경우 전체 리스트 출력
        } else {
            list = boardService.boardSeachList(searchKeyword,pageable);
        }

        int nowPage=list.getPageable().getPageNumber() + 1; //pageable이 가ㅣㅈ고 있는 페이지는 0부터 시작하기 때문에 1을 더함
        int startPage = Math.max(nowPage -4,1); //1보다 작은 경우는 1을 반환
        int endPage = Math.min(nowPage + 5,list.getTotalPages()); //전체 페이지보다 많은 경우는 전체 페이지를 반환
        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "boardlist";
    }
    @GetMapping("/board/view") //localhost:8080/board/view?id=1
    public String boardView(Model model,Integer id){
        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDlete(id);
        return "redirect:/board/list";
    }
}