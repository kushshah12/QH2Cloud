package qhcloud.selenium.TestSuite;

//import org.apache.tools.ant.taskdefs.Length;


public class OTPConversion {

	public static void main(String[] args) {
		
		

		String urlStr= "a3\"EC?";
		String strEncoded =new OTPConversion().urlEscapeCode(urlStr);
		System.out.println(strEncoded);
			
		}
	
	
	
	public String urlEscapeCode (String urlStr){
		
		String encodedStr = urlStr;
		
		encodedStr = encodedStr.replace("%", "%25");//Replacing % first as escape sequence itself contains % character
		encodedStr = encodedStr.replace(" ", "%20");
		encodedStr = encodedStr.replace("<", "%3C");
		encodedStr = encodedStr.replace(">", "%3E");
		encodedStr = encodedStr.replace("#", "%23");
		encodedStr = encodedStr.replace("{", "%7B");
		encodedStr = encodedStr.replace("}", "%7D");
		encodedStr = encodedStr.replace("|", "%7C");
		encodedStr = encodedStr.replace("\"", "%5C");
		encodedStr = encodedStr.replace("^", " %5E");
		encodedStr = encodedStr.replace("~", "%7E");
		encodedStr = encodedStr.replace("[", "%5B");
		encodedStr = encodedStr.replace("]", "%5D");
		encodedStr = encodedStr.replace("'", "%60");
		encodedStr = encodedStr.replace(";", "%3B");
		encodedStr = encodedStr.replace("/", "%2F");
		encodedStr = encodedStr.replace("?", "%3F");
		encodedStr = encodedStr.replace(":", "%3A");
		encodedStr = encodedStr.replace("@", "%40");
		encodedStr = encodedStr.replace("=", "%3D");
		encodedStr = encodedStr.replace("&", "%26");
		encodedStr = encodedStr.replace("$", "%24");
		
		
		
		return encodedStr;
	}
}


	
	
	