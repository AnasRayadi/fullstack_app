package com.rayadi.backend.repository;

import com.rayadi.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookRepo extends JpaRepository<Book, Long> , QuerydslPredicateExecutor<Book> {

    /* @Query("select b from Book b where " +
            "(:categoryId is null or b.category.categoryId = :categoryId) " +
            "and ((:startDate is null and :endDate is null)  or (b.edition >= :startDate and b.edition <=:endDate)) ")
            //+"and (:endDate is null or b.edition <= :endDate)")
    List<Book> filter(@Param("categoryId") Integer categoryId,
                      @Param("startDate") LocalDate startDate,
                      @Param("endDate") LocalDate endDate);*/
    boolean existsByTitleIgnoreCase(String title);

}
