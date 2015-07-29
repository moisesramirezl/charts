package in.webtuts.google.chart.spring.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.webtuts.google.chart.data.PieChartData.KeyValue;
import in.webtuts.google.chart.spring.services.PieChartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/piechart")
public class PieChartController {

    @Autowired
    private PieChartService pieChartService;
    
    

    @RequestMapping(method = RequestMethod.GET)
    public String springMVC(ModelMap modelMap, 
    		@RequestParam(value = "from", required = false, defaultValue = "2015-07-20") String fromString,
    		@RequestParam(value = "to", required = false, defaultValue = "2015-07-29") String toString) throws ParseException {
    	    	    
    	
    		
		java.sql.Date fromM =  java.sql.Date.valueOf(fromString);
		java.sql.Date toM =  java.sql.Date.valueOf(toString);
				
		
        List<KeyValue> pieDataList = pieChartService.getPieChartData(fromM, toM);
        modelMap.addAttribute("pieDataList", pieDataList);
        return "spring";
    }

}