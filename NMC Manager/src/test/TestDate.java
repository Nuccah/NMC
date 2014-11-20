package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TestDate {

	@Test
	public void test() {
		String date = "16/07/1989";
		long sql = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date parsed;
			try {
				parsed = format.parse(date);
				sql = parsed.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       java.sql.Date newDate = new java.sql.Date(sql);
	       System.out.println(newDate);
	}
}
