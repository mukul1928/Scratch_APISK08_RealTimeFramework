package Utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import POJOMapper.CreateUserPOJO;

public class CreateUserData {
	
	public static String CreateUser() throws JsonProcessingException {
	
	CreateUserPOJO emp = new CreateUserPOJO();
		emp.setId(1234);
		emp.setName("Chandan");
		emp.setSalary(200000f);
		emp.setMarried(true);
		
		ObjectMapper obj = new ObjectMapper();
		String emp_JSON = obj.writerWithDefaultPrettyPrinter().writeValueAsString(emp);
		return emp_JSON;
	}
}
