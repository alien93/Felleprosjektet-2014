package models;

import java.util.GregorianCalendar;

public class AvtaleBokModel {
	
	private GregorianCalendar currentDate;
	private int week;
	private int year;
	private int month;
	
	public AvtaleBokModel() {
		init();
	}
	
	public void init() {
		currentDate = new GregorianCalendar();
		year = currentDate.get(GregorianCalendar.YEAR);
		week = currentDate.get(GregorianCalendar.WEEK_OF_YEAR);
		month = currentDate.get(GregorianCalendar.MONTH);
	}
	
	public int getWeek() {
		return week;
	}
	
	public void setWeek(int week) {
		this.week = week;
		currentDate.set(GregorianCalendar.WEEK_OF_YEAR, week);
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
}
