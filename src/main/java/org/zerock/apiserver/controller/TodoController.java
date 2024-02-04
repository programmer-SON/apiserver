package org.zerock.apiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.apiserver.dto.PageRequestDTO;
import org.zerock.apiserver.dto.PageResponseDTO;
import org.zerock.apiserver.dto.TodoDTO;
import org.zerock.apiserver.service.TodoService;

@RestController
@Log4j2
@RequiredArgsConstructor // 자동주입
@RequestMapping("/api/todo")
public class TodoController{

    private final TodoService todoService;

    // PathVariable : 항상성을 유지
    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable("tno") Long tno){

        return todoService.get(tno);
    }

    // queryString : 가변적. 주로 page를 쓸때 쓰임
    @GetMapping("/list")
    public PageResponseDTO<TodoDTO>  list(PageRequestDTO pageRequestDTO){

        log.info("list.............." + pageRequestDTO);

        return todoService.getList(pageRequestDTO);
    }

}
