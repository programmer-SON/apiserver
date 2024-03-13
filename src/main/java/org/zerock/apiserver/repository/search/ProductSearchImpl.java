package org.zerock.apiserver.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.apiserver.domain.Product;
import org.zerock.apiserver.domain.QProduct;
import org.zerock.apiserver.domain.QProductImage;
import org.zerock.apiserver.dto.PageRequestDTO;
import org.zerock.apiserver.dto.PageResponseDTO;
import org.zerock.apiserver.dto.ProductDTO;

import java.util.List;
import java.util.Objects;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO) {

        log.info("----------------searchList--------------");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() -1, //페이지 0부터 시작할것이기 때문에 -1
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<Product> query = from(product);

        //product.imageList를 productImage로 간주
        //elementCollection의 querydsl 사용 방법
        query.leftJoin(product.imageList, productImage);

        query.where(productImage.ord.eq(0));

        Objects.requireNonNull(getQuerydsl().applyPagination(pageable, query));

        // Product만 출력
        List<Product> productList = query.fetch();

        // Product와 ProductImage 까지 같이 조회)
        List<Tuple> productList2 = query.select(product, productImage).fetch();

        long count = query.fetchCount();

        log.info("==============================================");
        log.info(productList);
        log.info(productList2);

        return null;
    }
}
