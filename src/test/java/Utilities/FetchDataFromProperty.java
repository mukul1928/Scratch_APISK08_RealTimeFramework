package Utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import ConstantsData.Constants1;

//Properties is such kind of a class which store the data in a key value pair


public class FetchDataFromProperty {
	static FileReader reader;

	public static Properties readDataFromProperty()
	{
			try {
				reader = new FileReader(Constants1.propFilePath);
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			Properties prop = new Properties();
			try {
				prop.load(reader);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return prop;
	}

}
