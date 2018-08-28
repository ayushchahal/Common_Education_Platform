package dynamicResponses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HTMLReader {
	
	public String readHTMLFile(String location)
	{
		StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new FileReader(location));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		//System.out.println(content);
		return content;
	}

}
