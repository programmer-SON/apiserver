package org.zerock.apiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.apiserver.dto.PageRequestDTO;
import org.zerock.apiserver.dto.PageResponseDTO;
import org.zerock.apiserver.dto.TodoDTO;
import org.zerock.apiserver.service.TodoService;

import java.util.Map;

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

    @PostMapping("/")
    // @RequestBody는 Json 데이터를 받기 위해 필요
    public Map<String, Long> register(@RequestBody TodoDTO dto){
        log.info("todoDTO: " + dto);

        Long tno = todoService.register(dto);

        return Map.of("TNO", tno);
    }

}
