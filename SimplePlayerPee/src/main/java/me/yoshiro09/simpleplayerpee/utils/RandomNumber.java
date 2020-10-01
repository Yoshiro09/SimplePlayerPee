package me.yoshiro09.simpleplayerpee.utils;

import java.text.DecimalFormat;
import java.util.Random;

public class RandomNumber {
	
	public double getRandomNumber(double min, double max) {
		Random r = new Random();
		double randomValue = min + (max - min) * r.nextDouble();
		DecimalFormat df = new DecimalFormat("#.##");
		
		return Double.parseDouble(df.format(randomValue));
	}
	
}
