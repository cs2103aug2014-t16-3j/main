package udo.tests;

import java.util.Arrays;
import java.util.Calendar;

import udo.util.shared.ItemData;
import udo.util.shared.ItemType;

/**
 * should not be in production code
 * 
 *
 */
public class RandomTesting {

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		String s = "a||||||a";
		String ss = "a:d:::::f:s";
		String sss = "a, b, d, f, qweqw/:eqwe";
		String[] sa = s.split("\\|\\|\\|");
		String[] ssa = ss.split(":");
		String[] sssa = sss.split(", ");
		ssa.toString(); sssa.toString();
		System.out.println(Arrays.toString(sa));

		c.set(Calendar.YEAR, 2001);
		System.out.println(c.toString());
		c.set(Calendar.YEAR, 01);
		System.out.println(c.toString());
		c.set(Calendar.AM_PM, Calendar.PM);
		String f = "%1$d";
		System.out.println(String.format(f, c.get(Calendar.AM_PM)));
		println(ItemType.EVENT.toString());
		ItemData i = new ItemData(ItemType.TASK);
		println(i.getItemType().toString());
		ItemData d = i;
		println(d.getItemType().toString());
		d.put("", sss);
		println(d.get("").toString());
		println(i.get("").toString());
		
	}

	static void println(String s) {
		System.out.println(s);
	}

}
