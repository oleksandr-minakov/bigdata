package com.mirantis.aminakov.bigdatacourse.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.DaoHDFS;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.Pair;
import com.mirantis.aminakov.bigdatacourse.mapreduce.GetParsedStatistics;
import com.mirantis.aminakov.bigdatacourse.mapreduce.JobRunner;
import com.mirantis.aminakov.bigdatacourse.mapreduce.WordCounterJob;
import com.mirantis.aminakov.bigdatacourse.mapreduce.WordCounterJob.Map;
import com.mirantis.aminakov.bigdatacourse.mapreduce.WordCounterJob.Reduce;
import com.mirantis.aminakov.bigdatacourse.service.Service;
import com.mirantis.aminakov.bigdatacourse.service.StatService;
@SuppressWarnings({"unused"})
@Controller
public class StatisticsController {
	
	private StatService statService;
	
	@Autowired
	public void setService(StatService statService) {
		
		this.statService = statService;
		this.statService.setUpService();
	}
	
	@RequestMapping(value = "/statview", method=RequestMethod.GET)
	public String getStatView(Model model) throws IOException, DaoException{
		
		List<Pair<String, Double>> pairs = new ArrayList<Pair<String, Double>>();
		pairs = this.statService.viewStatistics();
		
		model.addAttribute("pairs", pairs);
		return "statview";
	}
	
	@RequestMapping(value = "/recalculate", method=RequestMethod.GET)
	public String recalculate(Model model) throws IOException, DaoException{
		
		String msg = new String("MapReduce servise is busy");
		List<Pair<String, Double>> pairs = new ArrayList<Pair<String, Double>>();
		
		pairs = this.statService.recalculateStatistics();
		
		model.addAttribute("status", "MapReduce servise is now running. Please wait...");
		
		return "recalculate";
	}
	
	@RequestMapping(value = "/statistics", method=RequestMethod.GET)
	public String getStatistics(Model model) throws IOException, DaoException{
		
		boolean instanceof_flag = true;
		boolean flag = true;
		
		if(!(this.statService.getDao() instanceof DaoHDFS)){
			
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
		

		if(this.statService.getPool().getActiveCount() != 0){
			flag = false;
			model.addAttribute("avaibility", "Previous statistics were deleted. MapReduce servise calculating new one.");
		}
		else
			flag = true;
		
		model.addAttribute("instanceof_flag", instanceof_flag);
		model.addAttribute("flag", flag);
		
		return "statistics";
	}
	
}
