package in.webtuts.google.chart.spring.daos;

import in.webtuts.google.chart.data.PieChartData;
import in.webtuts.google.chart.data.PieChartData.KeyValue;

import java.util.Date;
import java.util.List;

public class PieChartDaoImpl implements PieChartDao {

    @Override
    public List<KeyValue> getPieChartData(Date from, Date to) {    	
        return PieChartData.getPieDataList(from, to);
    }

}