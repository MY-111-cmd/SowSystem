package ClientDemo;

import java.io.UnsupportedEncodingException;

public class Aclass {
	  public static String DLL_PATH;
	    static{
	        String path=(ClientDemo.class.getResource("/").getPath()).replaceAll("%20", " ").substring(1).replace("/", "\\");
	        try {
	            DLL_PATH= java.net.URLDecoder.decode(path,"utf-8");
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }


	    }
}
