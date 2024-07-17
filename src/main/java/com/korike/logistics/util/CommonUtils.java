package com.korike.logistics.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.korike.logistics.entity.CustomerOrderDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;



public class CommonUtils {
	
	public static void main(String[] args) {}
	
	private static final int TEN_MINUTES = 10 * 60 * 1000;

	public static String printException(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String stackTrace = sw.toString();
		return stackTrace;
	}
	
	
	public static int generateOtp() {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		return otp;
	}

	public static int generateRandomNumberWithTimestamp() {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		return otp;
	}
	
	public static boolean isOtpExpired(Timestamp created) {
		long tenAgo = System.currentTimeMillis() - TEN_MINUTES;
		if (created.getTime() < tenAgo) {
		    System.out.println("searchTimestamp is older than 10 minutes");
		    return true;
		}
		return false;
	}
	
	public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
 
        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
     
    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);
 
        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));
 
        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }
 
        return hexString.toString();
    }
	
	public static String generateOrdNum() {
		 return "O"+generateOtp();
	}
	public static String generateInvoiceNum() {
		 return "E-"+generateOtp();
	}
	
	public static String getCurrentUserName() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		
        String username = userDetails.getUsername();
        
        return username;
	}
	
	 public static Timestamp convertStringToTimestamp(String strDate) {
		    try {
		      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		       // you can change format of date
		      Date date = formatter.parse(strDate);
		      Timestamp timeStampDate = new Timestamp(date.getTime());

		      return timeStampDate;
		    } catch (ParseException e) {
		      System.out.println("Exception :" + e);
		      return null;
		    }
		  }
	 
	 public static String generateFileName(MultipartFile multiPart) {
		    return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
		}
	 
	 public static File convertMultiPartToFile(MultipartFile file) throws IOException {
		    File convFile = new File(file.getOriginalFilename());
		    FileOutputStream fos = new FileOutputStream(convFile);
		    fos.write(file.getBytes());
		    fos.close();
		    return convFile;
		}
	 
	 
	 public static long getDiffereceInMin(Date d1, Date d2) {
	
				 long duration  = d2.getTime() - d1.getTime();

				 long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
				 long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
				 long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
				 long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
				 
				 return diffInMinutes;
	 }
	 public static String getDateAsWords(Date date) {
		 SimpleDateFormat month_date = new SimpleDateFormat("MMM-yyyy", Locale.ENGLISH);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		//	String actualDate = "2016-03-20";


			String month_name = month_date.format(date);
			System.out.println("Month :" +date.getDate()+"-"+ month_name);  //Mar 2016
			return date.getDate()+"-"+ month_name;
	 }

	 public static String stripUsername(String username){
		String[] arrOfStr = username.split("_", 2);
		return arrOfStr[0];
	 }

	public static String appendUsernameWithAuthtype(String username, Integer authType){
		String arrOfStr = username.concat("_"+authType);
		return arrOfStr;
	}

}
