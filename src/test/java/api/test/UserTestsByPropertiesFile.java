package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.Routes;
import api.endpoints.UserEndpoints;
import api.endpoints.UserEndpoints_FromPropertiesFile;
import api.payload.User;
import io.restassured.response.Response;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

public class UserTestsByPropertiesFile {

	Faker faker; 
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setup() {
		faker = new Faker();
		
		userPayload= new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger = LogManager.getLogger(this.getClass()); 
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext)LogManager.getContext(false);
		File file = new File ("log4j2.properties");
		context.setConfigLocation(file.toURI());
		System.setProperty("log4j.configurationFile", "log4j2.properties");

	}
	
	@Test(priority=1)
	public void testPostUser() {
	
		logger.info("--------Executing testPostUser method----------");
		Response response = UserEndpoints_FromPropertiesFile.createuser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test (priority=2)
	public void testGetUserByName() {
		logger.info("--------Executing testGetUserByName method----------");
		System.out.println("------------ "+this.userPayload.getUsername());
		
		Response response = UserEndpoints_FromPropertiesFile.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);	
	}
	
	@Test(priority=3)
	public void testUpdateUser() {
		logger.info("--------Executing testUpdateUser method----------");
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndpoints_FromPropertiesFile.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
//		response.then().log().body().statusCode(200);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		logger.info("--------Executing testDeleteUserByName method----------");
		Response response = UserEndpoints_FromPropertiesFile.deleteUser(this.userPayload.getUsername());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	
	
}
