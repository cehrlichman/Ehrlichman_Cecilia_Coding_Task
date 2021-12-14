import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.io.FileNotFoundException;

/**
* 
* most_active_cookie takes in a csv file that contains a list of cookies and timestamps sorted by 
* timestamp. It requires a command line argument of the csv file, a flag -d to indicate where the 
* date is on the line and a date. Then it identifies the flag -d and constructs a most_active_cookie
* object which stores a list of cookies from that date. Using this list, it uses the 
* calculateActivityMode() method to find the mode in the list CookiesOnGivenDate. 
* calculateActivityMode() uses a hashmap of frequencies to find the mode. All the cookies that appear
* the maximum number of times are printed to the output stream. 
*
* Test Cases:
* In order to see the test cases, Run:
* java most_active_cookie.java test
* 
* Example Compilation: 
* javac most_active_cookie.java
*
* Example Run:
* java most_active_cookie.java -d 2018-12-08 cookie_log.csv
* 
* Expected Output: 
* fbcn5UAVanZf6UtG
* SAZuXPGUrfbcn5UA
* 4sMM2LxV07bPJzwf
* 
* Example Run2: 
* java most_active_cookie.java -d 2018-12-09 cookie_log.csv
* 
* Expected Output: 
* AtY0laUfhglK3lC7
*
*
**/

public class most_active_cookie {

  //instance variable
  private List<String> CookiesOnGivenDate;

  //main method
  public static void main (String [] args){

      if (args.length == 3){ 

        int flag = 0;

        while (flag < args.length && !args[flag].equals("-d")){
          flag++;	
        }

        most_active_cookie Most_active_cookie = new most_active_cookie(args[(flag+2)%args.length], args[flag+1]);
        print(Most_active_cookie.calculateActivityMode());
      }

      // otherwise, run the test cases
      else if (args[0].equals("test")){

        // test case 1
        System.out.println("test case 1: ");
        HashSet<String> output1 = new HashSet<String>();
        output1.add("AtY0laUfhglK3lC7");
        test("cookie_log.csv", "2018-12-09", output1);
	System.out.println();

        // test case 2
        System.out.println("test case 2: ");
        HashSet<String> output2 = new HashSet<String>();
        output2.add("fbcn5UAVanZf6UtG");
        output2.add("SAZuXPGUrfbcn5UA");
        output2.add("4sMM2LxV07bPJzwf");
        test("cookie_log.csv", "2018-12-08", output2);
	System.out.println();

        // test case 3
        System.out.println("test case 3: ");
        HashSet<String> output3 = new HashSet<String>();
        output3.add("4sMM2LxV07bPJzwf");
        test("cookie_log.csv", "2018-12-07", output3);
	System.out.println();

        // test case 4
        System.out.println("test case 4: ");
        HashSet<String> output4 = new HashSet<String>();
        test("cookie_log.csv", "2018-12-12", output4w);
	System.out.println();

      }


  }

  //constructor which reads in the file and adds the cookies on the entered date to the instance variable list CookiesOnGivenDate
  public most_active_cookie(String fileName, String date){
    CookiesOnGivenDate = new ArrayList<String>();

    File inputFile = new File(fileName);
    Scanner scanner = null;

    try {
        scanner = new Scanner(inputFile);
        }
    catch (FileNotFoundException e) {
        System.err.println(e);
        System.exit(0);
        }

    // iterate through each cookie name and timestamp in the file
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String [] cookieData = line.split(",");

      // add the cookies on the given date to the list CookiesOnGivenDate
      if (cookieData[1].substring(0,10).equals(date)){
        CookiesOnGivenDate.add(cookieData[0]);
      }

    }
    scanner.close();

  }

  // method that calculates and prints the cookie that appears the most times to the output stream 

  public Set<String> calculateActivityMode(){

    // map to store the cookies and the number of times they occur in the list CookiesOnGivenDate
    HashMap<String, Integer> cookieFrequency = new HashMap<>();

    // fill map cookieFrequency with all the frequencies
    for (String cookie : CookiesOnGivenDate) {

      if (cookieFrequency.containsKey(cookie)){
        cookieFrequency.put(cookie, cookieFrequency.get(cookie) + 1);
      }
      else{
        cookieFrequency.put(cookie, 1);
      }
    }

    // find the maximum frequency
    int max = 0;
    for (String cookie : cookieFrequency.keySet()) {
      if (cookieFrequency.get(cookie) > max){
        max = cookieFrequency.get(cookie);
      }
    }

    // returns a set of the cookies with the maximum frequency
    Set<String> toReturn = new HashSet<String>();
    for (String cookie : cookieFrequency.keySet()) {
      if (cookieFrequency.get(cookie) == max){
        toReturn.add(cookie);
      }
    }
    return toReturn;
  }

  public static void print(Set<String> toPrint){
    for (String s : toPrint){
      System.out.println(s);
    }

  }

  // tests if the program creates the expected output
  public static Boolean test(String fileIn, String timeIn, HashSet<String> expectedOut){
    most_active_cookie testCookies = new most_active_cookie(fileIn, timeIn);
    System.out.println("Filename: " + fileIn);
    System.out.println("Date Input: " + timeIn);
    System.out.println("Expected Output: " + expectedOut);
    System.out.println("Actual Output: " + testCookies.calculateActivityMode());
    System.out.println("Meaning the test result is: " + testCookies.calculateActivityMode().equals(expectedOut));
    return testCookies.calculateActivityMode().equals(expectedOut);
  }

}

