package com.mirantis.aminakov.bigdatacourse.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	@RequestMapping(method=RequestMethod.GET)
	public String addForm() {
		return "addbook";
	}
	
	@RequestMapping("/")
    public String home() {
        return "redirect:/welcome";
    }
	
	@RequestMapping(value = "/search", method=RequestMethod.GET)
	public String getBooks(Map<String, Object> map, /*String find, String by,*/
			@RequestParam Integer pageNum, Model model) {
		int pageSize = 10;
		List<Book> books = new ArrayList<Book>();
//		if (find.equalsIgnoreCase("")) {
			books = service.getAllBooks(pageNum, pageSize);
			map.put("books", books);
			model.addAttribute("numberOfPages", books.size());
			model.addAttribute("currentPage", pageNum);
//		} else {
//			map.put("books", service.getAllBooks(pageNum, pageSize));
			/*switch (by) {
			case "title":
					map.put("books", service.findByTitle(pageNum, pageSize, find));
				break;
				
			case "author":
					map.put("books", service.findByAuthor(pageNum, pageSize, find));		
				break;
				
			case "genre":
					map.put("books", service.findByGenre(pageNum, pageSize, find));
				break;
				
			case "text":
					map.put("books", service.findByText(pageNum, pageSize, find));
				break;

			default:
				break;
			}*/
//		}
		
		return "search";
	}
	
	/*@RequestMapping(value = "/search", method=RequestMethod.POST)
	public String findBooks(Map<String, Object> map) {
		int pageNum = 1;
		int pageSize = 10;
		map.put("books", service.getAllBooks(pageNum, pageSize));
		return "search";
	}*/
	
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
		int id = 0;
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
}
