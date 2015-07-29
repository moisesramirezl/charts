package in.webtuts.google.chart.spring.controllers;

import java.util.List;

import in.webtuts.google.chart.data.PieChartData.KeyValue;
import in.webtuts.google.chart.spring.services.PieChartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/piechart")
public class PieChartController {

    @Autowired
    private PieChartService pieChartService;
    
    

    @RequestMapping(method = RequestMethod.GET)
    public String springMVC(ModelMap modelMap) {
        List<KeyValue> pieDataList = pieChartService.getPieChartData();
        modelMap.addAttribute("pieDataList", pieDataList);
        return "spring";
    }

}