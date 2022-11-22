package com.adv.util;

public class SortingImp {

	public SortingImp() {
		super();

	}

	public static String getSort(String col) {
		int i = 0;
		String colFinal="";
		String col1="";
		String col2="";
		
		while (i < 3) {
			switch (i) {
			case 0: 
				if(col.toLowerCase().equalsIgnoreCase("firstname")&& !col.toLowerCase().equalsIgnoreCase("")) {
					
					col1=col.toLowerCase().substring(0, 5);
					col2=col.toLowerCase().substring(5, 9);
					colFinal=col1+"_"+col2;
				
				}
				
			case 1: 
				if(col.toLowerCase().equalsIgnoreCase("lastname")&& !col.toLowerCase().equalsIgnoreCase("")) {
					
					col1=col.toLowerCase().substring(0, 4);
					col2=col.toLowerCase().substring(4, 8);
					colFinal=col1+"_"+col2;
				
				}
				
			case 2: 
				if(col.toLowerCase().equalsIgnoreCase("username")&& !col.toLowerCase().equalsIgnoreCase("")) {
					
					col1=col.toLowerCase().substring(0, 4);
					col2=col.toLowerCase().substring(4, 8);
					colFinal=col1+"_"+col2;
				
				}
				default:
			}
          i++;
		}
		return colFinal;
	}
}
