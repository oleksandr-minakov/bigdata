package com.mirantis.bigdatacourse.webapp;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.DaoHDFS;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.Pair;
import com.mirantis.bigdatacourse.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings({"unused"})
@Controller
public class StatisticsController {
	
	private StatService statService;
	
	@Autowired(required = false)
	public void setService(StatService statService) {
		
		this.statService = statService;
		this.statService.setUpService();
	}
	
	@RequestMapping(value = "/statview", method=RequestMethod.GET)
	public String getStatView(Model model) throws IOException, DaoException {
		
		if(!(this.statService == null)) {
			List<Pair<String, String>> pairs = new ArrayList<Pair<String, String>>();
			pairs = this.statService.viewStatistics();
			
			model.addAttribute("pairs", pairs);
			return "statview";
		}
		return "statview";
	}
	
	@RequestMapping(value = "/recalculate", method=RequestMethod.GET)
	public String recalculate(Model model) throws IOException, DaoException {
		
		String msg = new String("MapReduce servise is busy");
		List<Pair<String, String>> pairs = new ArrayList<Pair<String, String>>();
		
		pairs = this.statService.recalculateStatistics();
		
		model.addAttribute("status", "MapReduce servise is now running. Please wait...");
		
		return "recalculate";
	}
	
	@RequestMapping(value = "/statistics", method=RequestMethod.GET)
	public String getStatistics(Model model) throws IOException, DaoException {
		
		if(!(this.statService == null)) {
			boolean instanceof_flag = true;
			boolean flag = true;
			
			if(!(this.statService.getDao() instanceof DaoHDFS)) {
				
				flag = true;
				instanceof_flag = false;
				model.addAttribute("instance", "No Statistics available. Swith to Hadoop and redeploy your app.");
				return "statistics";
			}
			else{
				
				flag = true;
				instanceof_flag = true;
				model.addAttribute("flag", flag);

			}
			

			if(this.statService.getPool().getActiveCount() != 0) {
				flag = false;
				model.addAttribute("avaibility", "Previous statistics were deleted. MapReduce servise calculating new one.");
			}
			else
				flag = true;
			
			model.addAttribute("instanceof_flag", instanceof_flag);
			model.addAttribute("flag", flag);
			return "statistics";
		}
		
		return "statistics";
	}
	
}
