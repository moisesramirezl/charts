package in.webtuts.google.chart.spring.daos;

import in.webtuts.google.chart.data.PieChartData.KeyValue;

import java.util.Date;
import java.util.List;

public interface PieChartDao {

    List<KeyValue> getPieChartData(Date from, Date to);

}