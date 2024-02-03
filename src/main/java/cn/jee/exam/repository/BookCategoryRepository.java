package cn.jee.exam.repository;

import cn.jee.exam.entity.BookCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCategoryRepository extends CrudRepository<BookCategory, Long> {

    List<BookCategory> findAll();
}
