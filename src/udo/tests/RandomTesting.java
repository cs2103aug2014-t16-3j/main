package udo.tests;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

import udo.util.shared.ItemType;

public class RandomTesting {

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		String s = "/a/d/f/|s";
		String ss = "a:d:f:s";
		String sss = "a, b, d, f, qweqw/:eqwe";
		String[] sa = s.split("\\" + "|");
		String[] ssa = ss.split(":");
		String[] sssa = sss.split(", ");
		System.out.println(Arrays.toString(sa));

		c.set(Calendar.YEAR, 2001);
		System.out.println(c.toString());
		c.set(Calendar.YEAR, 01);
		System.out.println(c.toString());
		c.set(Calendar.AM_PM, Calendar.PM);
		String f = "%1$d";
		System.out.println(String.format(f, c.get(Calendar.AM_PM)));
		println(ItemType.EVENT.toString());

		Scanner sc = new Scanner(System.in);
		sc.nextLine();
	}

	static void println(String s) {
		System.out.println(s);
	}

}
