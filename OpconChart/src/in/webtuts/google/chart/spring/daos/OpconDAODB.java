package in.webtuts.google.chart.spring.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.webtuts.google.date.ChileFixedHolidays;
import in.webtuts.google.date.FixedHolidays;
import in.webtuts.google.date.HolidayException;
import in.webtuts.google.date.Holidays;
import in.webtuts.google.opcon.Opcon;

public class OpconDAODB implements OpconDAO{
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://10.10.46.4:3319/jiradb";

	   //  Database credentials
	   static final String USER = "maria_serrano";
	   static final String PASS = "WP-kHrY_x97Q!";
	   

	   @Override
	   public List<Opcon> getAllOpcons(Date from, Date to) {
		   List<Opcon> opcons = new ArrayList<Opcon>();
		   Connection conn = null;
		   Statement stmt = null;
		   int last_id = 0;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      
		      String sql;
		      sql = "select "
		      		+ "jiraissue.id AS ID, jiraissue.issuenum as ISSUENUM, changeitem.OLDSTRING AS STEP, changegroup.CREATED AS EXECUTED, jiradb.issuestatus.pname AS CURRENT_STATUS "
		      		+ "FROM jiraissue "
		      		+ "JOIN changegroup  on changegroup.issueid = jiraissue.ID "
		      		+ "JOIN changeitem  on changeitem.groupid = changegroup.id "
		      		+ "and changeitem.field = \"status\" "
		      		+ "LEFT JOIN nodeassociation X "
		      		+ "ON X.SOURCE_NODE_ID = jiraissue.ID and X.ASSOciATION_TYPE = 'IssueFixVersion' "
		      		+ "LEFT JOIN projectversion Y ON Y.ID = X.SINK_NODE_ID "
		      		+ "JOIN project "
		      		+ "ON project.ID = jiraissue.PROJECT "
		      		+ "AND project.pkey = 'CIL' "
		      		+ "JOIN issuestatus  "
		      		+ "ON jiraissue.issuestatus = issuestatus.ID "
		      		+ "WHERE jiraissue.issuetype = 3 "
		      		+ "AND jiraissue.UPDATED >= ? "
		      		+ "AND jiraissue.UPDATED <= ? "
//		      		+ "AND issuestatus.pname = 'Resolved' AND jiraissue.issuenum=? order BY jiraissue.issuenum, changegroup.CREATED";
		      		+ "AND issuestatus.pname = 'Resolved' order BY jiraissue.issuenum, changegroup.CREATED";
		      
		      java.sql.PreparedStatement preparedStatement = conn.prepareStatement(sql);
		      preparedStatement.setDate(1, (java.sql.Date) from); 
		      preparedStatement.setDate(2, (java.sql.Date) to);
		      
		      ResultSet rs = preparedStatement.executeQuery();

		      //STEP 5: Extract data from result set
		      
		      
		      Date dateInitialStatus = null;
		      Date dateFinishedStatus = null;
		      
		      String currentStatus = " ";
		      String lastStatus = " ";
		      
		      int numOfDaysInStatus = 0;
		      
		      while(rs.next()){
		         //Retrieve by column name
		    	  //System.out.println("CURRENT ID = " + rs.getInt("ID") + " LAST ID = " + last_id);
		    	  if(rs.getInt("ID") != last_id){		    		  
		    		  currentStatus = rs.getString("STEP"); 
		    		  Opcon o = new Opcon(rs.getInt("ID"), rs.getInt("ISSUENUM"));
		    		  System.out.println("Creando opcon con status = " + currentStatus);
		    		  o.setEstado(currentStatus, 0);
		    		  dateInitialStatus = rs.getDate("EXECUTED");		    		  
		    		  opcons.add(o);
		    		  
		    	  }else{		    		  
		    		  dateFinishedStatus = rs.getDate("EXECUTED");
		    		  currentStatus = rs.getString("STEP");
		    		  numOfDaysInStatus = Country.CHILE.getBusinessDayCount(dateFinishedStatus, dateInitialStatus);
		    		  
		    		  //System.out.println(dateFinishedStatus + " " + dateInitialStatus + " DIFF TIME = " + numOfDaysInStatus);
		    		  
		    		  if (opcons != null && !opcons.isEmpty()) {
		    			  System.out.println("Seteando estado = " + currentStatus + " Tiempo = " + numOfDaysInStatus);
		    			  opcons.get(opcons.size()-1).setEstado(currentStatus, numOfDaysInStatus);
		    			}
		    		  dateInitialStatus = dateFinishedStatus;
		    	  }
		    	  lastStatus = rs.getString("STEP");
		    	  last_id = rs.getInt("ID");
		        
		      }
		      //STEP 6: Clean-up environment
		      rs.close();
		      preparedStatement.close();
		      conn.close();
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
	      return opcons;
	   }
	   

	   
//De aqui hacia abajo esta asqueroso	   
	   public enum Country {

		    CHILE(new ChileFixedHolidays());
		    private Holidays fixedHolidays;  
		    private Country(FixedHolidays fixedHolidays) {
		        this.fixedHolidays = fixedHolidays;	    
		    }
		
		    public int getBusinessDayCount(Date d1, Date d2) throws HolidayException {
		        Calendar calendar = Calendar.getInstance();
		        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		        try {
		            d1 = formatter.parse(formatter.format(d1));
		            d2 = formatter.parse(formatter.format(d2));
		        } catch (ParseException ignore) {
		        }
//		        if (!isBusinessDay(d1) || !isBusinessDay(d2)) {
//		            throw new HolidayException("Dates must both be business days");
//		        }
		        int businessDayCount = 0;
		        Date min = d1.before(d2) ? d1 : d2;
		        Date max = min.equals(d2) ? d1 : d2;
		        calendar.setTime(min);
		        while (calendar.getTime().before(max)) {
		            calendar.add(Calendar.DAY_OF_MONTH, 1);
		            if (isBusinessDay(calendar.getTime())) {
		                businessDayCount++;
		            }
		        }
		        return businessDayCount;
		    }

		    public boolean isBusinessDay(Date date) {
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
		            return false;
		        } else if (fixedHolidays.contains(date)) {
		            return false;
		        }
		        return true;
		    }

		}	
		
		
}