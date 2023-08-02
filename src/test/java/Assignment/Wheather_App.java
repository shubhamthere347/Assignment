package Assignment;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Scanner;
public class Wheather_App {

	  private static final String API_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";
	    private Scanner sc;

	    @BeforeClass
	    public void setup() {
	        sc = new Scanner(System.in);
	    }

	    @AfterClass
	    public void tearDown() {
	        sc.close();
	    }

	    @Test
	    public void testWeatherApp() {
	        int choice;
	        do {
	            System.out.println("1. Get weather");
	            System.out.println("2. Get Wind Speed");
	            System.out.println("3. Get Pressure");
	            System.out.println("0. Exit");
	            System.out.print("Enter your choice: ");
	            choice = sc.nextInt();

	            switch (choice) {
	                case 1:
	                    getWeather();
	                    break;
	                case 2:
	                    getWindSpeed();
	                    break;
	                case 3:
	                    getPressure();
	                    break;
	                case 0:
	                    System.out.println("Exiting the program...");
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	                    break;
	            }
	        } while (choice != 0);
	    }
	    


	    private void getWeather() {
	        System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
	        String date = sc.next();

	        float temperature = getDataFromAPI(date, "main.temp");

	        System.out.println("Temperature on " + date + ": " + temperature + "°K");
	    }

	    private void getWindSpeed() {
	        System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
	        String date = sc.next();

	        float windSpeed = getDataFromAPI(date, "wind.speed");

	        System.out.println("Wind Speed on " + date + ": " + windSpeed + " m/s");
	    }

	    private void getPressure() {
	        System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
	        String date = sc.next();

	        float pressure = getDataFromAPI(date, "main.pressure");

	        System.out.println("Pressure on " + date + ": " + pressure + " hPa");
	    }

	      // float data;
	    private float getDataFromAPI(String date,String field) 
	    {
	    
	    	
	        Response response = RestAssured.given().log().all().when().get(API_URL)
	        		.then().log().all().extract().response();

	        
	        JsonPath jsonPath = response.jsonPath();
	      float data = 0.0f;
	        for (int i = 0; i < jsonPath.getInt("cnt"); i++)
           {
	            String dttxt = jsonPath.getString("list["+i+"].dt_txt");
	              
	         //System.out.println(dttxt+"----"+date);
	          
	            if (dttxt.contains(date))
	            {   //System.out.println(dttxt+" "+date+" "+dttxt.contains(date)+" "+jsonPath.getFloat("list["+i+"]."+field));
	            
	               data = jsonPath.getFloat("list["+i+"]."+field);
	              
	             // System.out.println(data);
	               // break;
	            }  
	        }
	        return data;
	    }
	
}
