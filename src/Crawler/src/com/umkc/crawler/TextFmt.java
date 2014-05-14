package com.umkc.crawler;

import java.awt.font.LineBreakMeasurer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class TextFmt {

	public static void main(String args[]){
		
		try {
			find("C:\\Users\\RajeshKannan\\Desktop\\Crawler\\Crawler\\input.in", "output.op");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void find(String input, String output) throws Exception {
		
		Scanner scan = new Scanner(new File(input));
		BufferedWriter out = new BufferedWriter(new FileWriter(output));
		boolean ingredians = false;
		boolean directions = false;
		boolean lineBreak = false;
		boolean preReady = false;
		boolean timeFlag = false;
		String ingr = "";
		String dir = "";
		String timeStr = "";
		//int test = Integer.parseInt(scan.nextLine());
		while(scan.hasNextLine()){
			String var = scan.nextLine().trim();
			if(var.equalsIgnoreCase("(Help)")){
				ingredians = true;
				continue;
			}
			if(var.equalsIgnoreCase("Directions")){
				ingredians = false;
				directions = true;
				continue;
			}
			if(var.equalsIgnoreCase("Kitchen-Friendly View")){
				directions = false;
				preReady = true;
				continue;
			}
			if(var.equalsIgnoreCase("READY IN") && preReady){
				timeFlag = true;
				preReady = false;
				continue;
			}
			if(var.equalsIgnoreCase("ADVERTISE WITH US")){
				timeFlag = false;
				continue;
			}
			if(ingredians){
				if(!var.equalsIgnoreCase("") ){
				ingr = ingr +" "+ var;
				lineBreak = true;
				}else if(lineBreak){
					ingr = ingr + "\n";
					lineBreak = false;
				}
			}
			if(directions){
				dir = dir + var;
			}
			if(timeFlag){
				if(!var.equalsIgnoreCase(""))
				timeStr = timeStr +" "+ var;
			}
			//out.write(var+"\n");
		}
		out.write(ingr+"\n");
		out.write("--------------------------------------------\n");
		out.write(dir+"\n");
		out.write("--------------------------------------------\n");
		out.write(timeStr+"\n");
		out.close();
		scan.close();
	}
}
