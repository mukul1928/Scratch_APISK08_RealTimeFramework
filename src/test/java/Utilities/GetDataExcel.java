package Utilities;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import ConstantsData.Constants1;





public class GetDataExcel {
	public static Map getExcelData() throws IOException {
		FetchDataFromExcel2 obj = new FetchDataFromExcel2(Constants1.ExcelPath,Constants1.SheetName);
		Map<String,Object> mp = new LinkedHashMap<String,Object>();
		mp.put("name", FetchDataFromExcel2.getData(1, 0));
		mp.put("gender", FetchDataFromExcel2.getData(1, 1));
		mp.put("email",CommonFunctions.generateRandomEmail());
		mp.put("status", FetchDataFromExcel2.getData(1, 2));
		return mp;
	}
}
