package cn.jee.exam.action;

import cn.jee.exam.entity.Book;
import cn.jee.exam.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RequestMapping("book")
@Controller
public class BookAction {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping("/category")
    public String getBooksByBookCategoryId(Model model, @RequestParam("category-id") long categoryId) {
        List<Book> books = bookRepository.findByCategoryId(categoryId);
        model.addAttribute("books", books);
        return "books";
    }

    @RequestMapping("/toAdd")
    public String toAdd(Model model, @RequestParam("category-id") long categoryId) {
        model.addAttribute("categoryId", categoryId);
        return "addBook";
    }

    @PostMapping("/add")
    public String add(Model model, @RequestParam("name") String name,
                      @RequestParam("categoryId") long categoryId,
                      @RequestParam("price") int price,
                      @RequestParam("description") String description) {
        Book book = new Book();
        book.setName(name);
        book.setCategoryId(categoryId);
        book.setPrice(price);
        book.setDescription(description);
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());

        Book bookInfoWithId = bookRepository.save(book);
        model.addAttribute("book", bookInfoWithId);
        return "redirect:/book/category?category-id=" + book.getCategoryId();
    }

    @RequestMapping("/toUpload")
    public String toUpload(Model model, @RequestParam("book-id") long bookId) {
        model.addAttribute("bookId", bookId);
        return "uploadBookCover";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(Model model, @RequestParam("book-id") long bookId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            model.addAttribute("error", "文件为空");
            return "error";
        }
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            model.addAttribute("error", "图书不存在");
            return "error";
        }
        try {
            // 获取上传文件的原始名称
            String fileName = file.getOriginalFilename();
            // TODO 改为 D:\image
            File uploadDir = new File("image");
            if (!uploadDir.exists()) {
              uploadDir.mkdir();
            }
            File destFile = new File(uploadDir.getAbsolutePath(), fileName);
            file.transferTo(destFile);

            book.setCover(destFile.getAbsolutePath());
            bookRepository.save(book);
            return "success";
        } catch (IOException ex) {
            ex.printStackTrace();
            model.addAttribute("error", "上传失败");
            return "error";
        }
    }
}
