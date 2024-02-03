package cn.jee.exam.repository;

import cn.jee.exam.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Override
    List<Book> findAll();

    List<Book> findByCategoryId(long categoryId);
}
