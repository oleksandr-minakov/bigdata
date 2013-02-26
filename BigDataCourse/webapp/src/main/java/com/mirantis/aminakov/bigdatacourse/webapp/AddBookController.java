package com.mirantis.aminakov.bigdatacourse.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.service.Service;

@Controller
public class AddBookController {
	
	private Service service;

	@Autowired
	public void setService(Service service) {
		this.service = service;
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
		int id;
		id = service.addBook(book);
		if (id != 0) {
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
		List<Book> books = new ArrayList<Book>();
		if (findString == null || findString.equalsIgnoreCase("")) {			//dangerous expression in condition
			books = service.getAllBooks(pageNum, pageSize);
			numberOfRecords = service.getNumberOfRecords();
			model.addAttribute("books", books);
//			model.addAttribute("numberOfPages", books.size());
			model.addAttribute("currentPage", pageNum);
			model.addAttribute("numberOfRecords", numberOfRecords);
		} else {
			model.addAttribute("findString",findString);
			model.addAttribute("findBy", findBy);
			by searchBy = by.valueOf(findBy);
			switch (searchBy) {
			case title:
				model.addAttribute("books", service.findByTitle(pageNum, pageSize, findString));
				model.addAttribute("numberOfRecords", service.getNumberOfRecords());
                model.addAttribute("currentPage", pageNum);
				break;
				
			case author:
				model.addAttribute("books", service.findByAuthor(pageNum, pageSize, findString));
				model.addAttribute("numberOfRecords", service.getNumberOfRecords());
                model.addAttribute("currentPage", pageNum);
				break;
				
			case genre:
				model.addAttribute("books", service.findByGenre(pageNum, pageSize, findString));
				model.addAttribute("numberOfRecords", service.getNumberOfRecords());
                model.addAttribute("currentPage", pageNum);
				break;
				
			case text:
				model.addAttribute("books", service.findByText(pageNum, pageSize, findString));
				model.addAttribute("numberOfRecords", service.getNumberOfRecords());
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
	
	/*@RequestMapping(value = "/search", method=RequestMethod.POST)
	public String findBooks(Map<String, Object> map) {
		int pageNum = 1;
		int pageSize = 10;
		map.put("books", service.getAllBooks(pageNum, pageSize));
		return "search";
	}*/
}
