package in.webtuts.google.chart.spring.daos;

import in.webtuts.google.chart.data.PieChartData;
import in.webtuts.google.chart.data.PieChartData.KeyValue;

import java.util.List;

public class PieChartDaoImpl implements PieChartDao {

    @Override
    public List<KeyValue> getPieChartData() {    	
        return PieChartData.getPieDataList();
    }

}