package in.webtuts.google.chart.spring.daos;

import java.util.Date;
import java.util.List;

import in.webtuts.google.opcon.Opcon;

public interface OpconDAO {
	public List<Opcon> getAllOpcons(Date from, Date to);	
}

