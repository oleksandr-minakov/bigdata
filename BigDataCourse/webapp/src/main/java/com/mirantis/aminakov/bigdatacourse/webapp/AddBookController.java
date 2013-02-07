package com.mirantis.aminakov.bigdatacourse.webapp;

import java.io.IOException;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mirantis.aminakov.bigdatacourse.service.Service;
//import com.mirantis.aminakov.bigdatacourse.dao.Book;

@Controller
@RequestMapping("/addbook")
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
	
	/*public String getAllBooks(Map<String, Object> map, int pageNum, int pageSize) {
		map.put("book", new Book());
		map.put("bookList", service.getAllBooks(pageNum, pageSize));
		return "index";
	}*/


	@RequestMapping(method=RequestMethod.POST)
	public void processUpload(@RequestParam MultipartFile file, Model model) throws IOException {
		model.addAttribute("message", "File '" + file.getOriginalFilename() + "' uploaded successfully");
	}	
}
