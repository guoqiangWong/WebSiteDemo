package com.manning.readinglist.dao;

import com.manning.readinglist.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ExpanseWong
 */
public interface ReadingListRepository extends JpaRepository<Book,Long> {
    /**
     * 根据读者名字查找图书
     * @param reader 读者名字
     * @return
     */
    List<Book> findByReader(String reader);
}
