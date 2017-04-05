package com.edu.seu.MusicSpider.SpiderUtiils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExp {
   public String  findPlayListLink(String txt) {
	   

		    String re1="(data)";	// Word 1
		    String re2="(-)";	// Any Single Character 1
		    String re3="(module)";	// Word 2
		    String re4="(=)";	// Any Single Character 2
		    String re5="(\".*?\")";	// Double Quote String 1
		    String re6="( )";	// White Space 1
		    String re7="(href)";	// Word 3
		    String re8="(=)";	// Any Single Character 3
		    String re9="(\")";	// Any Single Character 4
		    String re10=".*?";	// Non-greedy match on filler
		    String re11="(\")";	// Any Single Character 5
		    String re12="(>)";	// Any Single Character 6

		    Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8+re9+re10+re11+re12,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		    Matcher m = p.matcher(txt);
		    if (m.find())
		    {
		        String word1=m.group(1);
		        String c1=m.group(2);
		        String word2=m.group(3);
		        String c2=m.group(4);
		        String string1=m.group(5);
		        String ws1=m.group(6);
		        String word3=m.group(7);
		        String c3=m.group(8);
		        String c4=m.group(9);
		        String c5=m.group(10);
		        String c6=m.group(11);
		        System.out.print("("+word1.toString()+")"+"("+c1.toString()+")"+"("+word2.toString()+")"+"("+c2.toString()+")"+"("+string1.toString()+")"+"("+ws1.toString()+")"+"("+word3.toString()+")"+"("+c3.toString()+")"+"("+c4.toString()+")"+"("+c5.toString()+")"+"("+c6.toString()+")"+"\n");
		    }
			return re12;
		  
}	
	
}
