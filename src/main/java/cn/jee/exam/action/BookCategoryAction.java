package cn.jee.exam.action;

import cn.jee.exam.entity.BookCategory;
import cn.jee.exam.repository.BookCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("book-category")
@Controller
public class BookCategoryAction {

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @RequestMapping("/all")
    public String getAllCompanies(Model model) {
        List<BookCategory> bookCategories = bookCategoryRepository.findAll();
        model.addAttribute("bookCategories", bookCategories);
        return "bookCategories";
    }
}
