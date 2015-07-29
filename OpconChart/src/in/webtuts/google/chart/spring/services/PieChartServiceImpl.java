package in.webtuts.google.chart.spring.services;

import in.webtuts.google.chart.data.PieChartData.KeyValue;
import in.webtuts.google.chart.spring.daos.PieChartDao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class PieChartServiceImpl implements PieChartService {

    @Autowired
    private PieChartDao pieChartDao;

    public void setPieChartDao(PieChartDao pieChartDao) {
        this.pieChartDao = pieChartDao;
    }

    @Override
    public List<KeyValue> getPieChartData(Date from, Date to) {
        return pieChartDao.getPieChartData(from, to);
    }

}