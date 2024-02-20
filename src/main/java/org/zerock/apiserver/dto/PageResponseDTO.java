package org.zerock.apiserver.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// 특정한 DTO타입을 지정할수 있도록 Generic 사용
// Response는 받는 값이 거의 똑같아서 상속을 잘 안함
@Data
public class PageResponseDTO<E> {

    private List<E> dtoList;

    private List<Integer> pageNumList;

    // 검색조건, 현재페이지 etc.. 사용을 위해 추가
    private PageRequestDTO pageRequestDTO;

    // 이전, 다음 페이지
    private boolean prev, next;

    // 총 페이지수, 이전 시작 페이지, 다음 시작 페이지, 총 페이지, 현재 페이지
    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll") // 메소드 이름 지정
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long total){

        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int)total;

        //끝페이지 end
        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0))* 10;

        int start = end - 9;

        //진짜 마지막
        int last = (int) (Math.ceil((totalCount/(double)pageRequestDTO.getSize())));

        end = end > last ? last : end;

        this.prev = start > 1;

        this.next = totalCount > end * pageRequestDTO.getPage();

        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        this.prevPage = prev ? start -1 : 0;

        this.nextPage = next ? end + 1 : 0;

        this.totalPage = this.pageNumList.size();

        this.current = pageRequestDTO.getPage();

    }
}
