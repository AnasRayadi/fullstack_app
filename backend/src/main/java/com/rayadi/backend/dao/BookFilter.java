package com.rayadi.backend.dao;

import com.rayadi.backend.dto.FilterBooksRequest;
import com.rayadi.backend.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*@Repository
@RequiredArgsConstructor
public class BookFilter {
    private final EntityManager em;
    public List<Book> findAllByCriteria(String startDate , String endDate, String categoryId){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Book> bookRoot = criteriaQuery.from(Book.class);
        if(categoryId != null && !categoryId.equals("null") ){
            predicates.add(criteriaBuilder.equal(bookRoot.get("category").get("categoryId"),categoryId));
        }

        if(startDate != null && !startDate.equals("null")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(bookRoot.get("edition"), LocalDate.parse(startDate,formatter)));
            //predicates.add(criteriaBuilder.between(bookRoot.get("edition"), LocalDate.parse(request.getStartDate(),formatter), LocalDate.parse(request.getEndDate(),formatter)));
        }

        if(endDate != null && !endDate.equals("null") ){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            predicates.add(criteriaBuilder.lessThanOrEqualTo(bookRoot.get("edition"), LocalDate.parse(endDate,formatter)));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(criteriaQuery).getResultList();
    }
    *//*public List<Book> findAllByCriteria(FilterBooksRequest request){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Book> bookRoot = criteriaQuery.from(Book.class);
        if(request.getCategoryId() != null && !request.getCategoryId().equals("null") ){
            predicates.add(criteriaBuilder.equal(bookRoot.get("category").get("categoryId"),request.getCategoryId()));
        }
        if(request.getStartDate() != null && !request.getStartDate().equals("null")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(bookRoot.get("edition"), LocalDate.parse(request.getStartDate(),formatter)));
            //predicates.add(criteriaBuilder.between(bookRoot.get("edition"), LocalDate.parse(request.getStartDate(),formatter), LocalDate.parse(request.getEndDate(),formatter)));
        }

        if(request.getEndDate() != null && !request.getEndDate().equals("null") ){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            predicates.add(criteriaBuilder.lessThanOrEqualTo(bookRoot.get("edition"), LocalDate.parse(request.getEndDate(),formatter)));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(criteriaQuery).getResultList();
    }*//*
}*/
