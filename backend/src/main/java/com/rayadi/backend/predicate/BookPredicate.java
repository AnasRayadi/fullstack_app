package com.rayadi.backend.predicate;

import com.querydsl.core.BooleanBuilder;
import com.rayadi.backend.model.QBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class BookPredicate {

    public static BooleanBuilder getBookPredicate(Map<String, String> filters){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BooleanBuilder where = new BooleanBuilder();
        QBook book = QBook.book;

        var id = filters.get("categoryId");
        var startDate = filters.get("startDate");
        var endDate = filters.get("endDate");
        if (filters.containsKey("categoryId")){
            where.and(book.category.categoryId.eq(Integer.parseInt(filters.get("categoryId"))));
        }
        if (filters.containsKey("startDate")){
            where.and(book.edition.goe(LocalDate.parse(filters.get("startDate"),formatter)));
        }
        if (filters.containsKey("endDate")){
            where.and(book.edition.loe(LocalDate.parse(filters.get("endDate"),formatter)));
        }
        /*if (id != null && !id.equals("null")){
            where.and(book.category.categoryId.eq(Integer.parseInt(id)));
        }
        if (startDate != null && !startDate.equals("null") ){
            where.and(book.edition.goe(LocalDate.parse(startDate,formatter)));
        }
        if (endDate != null && !endDate.equals("null")){
            where.and(book.edition.loe(LocalDate.parse(endDate,formatter)));
        }*/

        /*
        where.and(book.category.categoryId.eq(Integer.parseInt(categoryId)));
        where.and(book.edition.goe(LocalDate.parse(startDate,formatter)));
        where.and(book.edition.loe(LocalDate.parse(endDate,formatter)));
        */
        return where;
    }
}
