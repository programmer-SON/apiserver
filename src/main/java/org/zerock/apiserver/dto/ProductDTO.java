package org.zerock.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long pno;

    private String pname;

    private int price;

    private String pdesc;

    // 삭제여부
    // 실제로 삭제하지 않고 Flag로 체크하는 이유는 다양한 외래키가 걸릴수 있기 때문에
    private boolean delFlag;

    @Builder.Default
    // 업로드 처리가 필요한 파일들
    private List<MultipartFile> files = new ArrayList<>();

    @Builder.Default
    // 조회할때 쓰는 파일이름
    private List<String> uploaderFileNames = new ArrayList<>();

}
