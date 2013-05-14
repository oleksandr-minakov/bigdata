package com.mirantis.bigdatacourse.webapp;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.PaginationModel;
import com.mirantis.bigdatacourse.service.Service;
import com.mirantis.bigdatacourse.service.restful.RestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class AddBookController {
	
	private Service service;
	private RestService restService;
    public static final Logger LOG = Logger.getLogger(AddBookController.class);

	@Autowired
	public void setService(Service service) {
		this.service = service;
	}
	
	@Autowired
	public void setService(RestService restService) {
		this.restService = restService;
	}
	
	@RequestMapping("/")
    public String home() {
        return "redirect:/welcome";
    }
	
	@RequestMapping(method=RequestMethod.GET)
	public String addForm() {
		return "addbook";
	}
	
	@RequestMapping(value = "/addbook", method=RequestMethod.POST)
	public String processUpload(@ModelAttribute("book") Book book, @RequestParam MultipartFile file, Model model) throws IOException {
		try {
			if(!file.isEmpty()) {
				validateFile(file);
				book.setText(file.getInputStream());
			}
		} catch (ContentTypeError e) {
			model.addAttribute("message", e.getMessage());
			return "upload";
		}
		int id = service.addBook(book);
		if (id == 0) {
			model.addAttribute("message", "File '" + file.getOriginalFilename() + "' uploaded successfully");
		} else {
			model.addAttribute("message", "File '" + file.getOriginalFilename() + "' upload failed");
		}
		model.addAttribute("book", book);
		model.addAttribute("id", id);
		model.addAttribute("text", book.getText());
		return "upload";
	}	
	
	private void validateFile(MultipartFile file) throws ContentTypeError {
		if (!file.getContentType().equals("text/plain")) {
			throw new ContentTypeError("ERROR. Only *.txt accepted.");
		}
	}
	
	@RequestMapping(value = "/search", method=RequestMethod.GET)
	public String getBooks(Integer pageNum, String findString, String findBy, Model model) {
		int numberOfRecords;
        int pageSize = 10;
		if (pageNum == null) {
			pageNum = 1;
		} else {
			//NOP
		}
		List<Book> books;
        PaginationModel booksModel;
		if (findString == null || findString.equalsIgnoreCase("")) {
			//dangerous expression in condition
			booksModel = service.getAllBooks(pageNum, pageSize);
            books = booksModel.getBooks();
			numberOfRecords = booksModel.getNumberOfRecords();
			model.addAttribute("books", books);
//			model.addAttribute("numberOfPages", books.size());
			model.addAttribute("currentPage", pageNum);
			model.addAttribute("numberOfRecords", numberOfRecords);
		} else {
			model.addAttribute("findString",findString);
			model.addAttribute("findBy", findBy);
			by searchBy = by.valueOf(findBy);
            PaginationModel searchBooksModel;
			switch (searchBy) {
			case title:
                searchBooksModel = service.findByTitle(pageNum, pageSize, findString);
                model.addAttribute("books", searchBooksModel.getBooks());
				model.addAttribute("numberOfRecords", searchBooksModel.getNumberOfRecords());
                model.addAttribute("currentPage", pageNum);
				break;
				
			case author:
                searchBooksModel = service.findByAuthor(pageNum, pageSize, findString);
                model.addAttribute("books", searchBooksModel.getBooks());
				model.addAttribute("numberOfRecords", searchBooksModel.getNumberOfRecords());
                model.addAttribute("currentPage", pageNum);
				break;
				
			case genre:
                searchBooksModel = service.findByGenre(pageNum, pageSize, findString);
                model.addAttribute("books", searchBooksModel.getBooks());
				model.addAttribute("numberOfRecords", searchBooksModel.getNumberOfRecords());
                model.addAttribute("currentPage", pageNum);
				break;
				
			case text:
                searchBooksModel = service.findByText(pageNum, pageSize, findString);
                model.addAttribute("books", searchBooksModel.getBooks());
				model.addAttribute("numberOfRecords", searchBooksModel.getNumberOfRecords());
                model.addAttribute("currentPage", pageNum);
				break;

			default:
				break;
			}
		}
		return "search";
	}
	
	public enum by {
		title,
		author,
		genre,
		text
	}
	
	@RequestMapping(value = "/text/{titleOfBook}", method=RequestMethod.GET)
	public String getTextOfBook(@PathVariable String titleOfBook, Model model) {
        if (titleOfBook == null || titleOfBook.equalsIgnoreCase(""))
            return "text";
        List<Book> books;
        PaginationModel booksModel;
        booksModel = restService.findByTitle(1, 1, titleOfBook);
        books = booksModel.getBooks();
        if (books.size() == 0)
            return "text";
        Book book = books.get(0);
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(book.getText(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return "text";
        }
        String line = null;
        try {
            if (bufferedReader != null) {
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return "text";
        }
        while(line != null){
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return "text";
            }
        }
//        LOG.info(inputStringBuilder.toString());
        model.addAttribute("text", inputStringBuilder.toString());
        return "text";
	}

    @RequestMapping(value = "/delete/{deleteBookId}", method=RequestMethod.GET)
    public String deleteBook(@PathVariable String deleteBookId, Model model) {
        if (deleteBookId.length() == 0)
            return "delete";
        int result = restService.delBook(deleteBookId);
        if (result == 0) {
            model.addAttribute("message", "Book with ID '" + deleteBookId + "' deleted successfully");
        } else {
            model.addAttribute("message", "Book with ID '" + deleteBookId + "' delete failed");
        }
        return "delete";
    }
}
