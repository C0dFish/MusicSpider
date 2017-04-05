package com.edu.seu.MusicSpider.SpiderUtiils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExp {
   public String  findPlayListLink(String txt) {
	   
	   String unixpath1 = null;
	   String re1=".*?";	// Non-greedy match on filler
	   String re2="(\\/discover\\/playlist)";	// Unix Path 1
	   

	    Pattern p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(txt);
	    if (m.find())
	    {
	      unixpath1=m.group(1);
	    }
		 
	    return unixpath1;
}	
	
}
