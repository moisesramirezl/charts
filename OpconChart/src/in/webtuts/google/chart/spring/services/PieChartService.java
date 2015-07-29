package in.webtuts.google.chart.spring.services;

import in.webtuts.google.chart.data.PieChartData.KeyValue;

import java.util.Date;
import java.util.List;

public interface PieChartService {

    List<KeyValue> getPieChartData(Date from, Date to);

}