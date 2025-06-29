package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
//	DataProvider 1
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException
	{
//		String path="./testData/Opencart_LoginData.xlsx"; //taking xlfile from testdata
		String path=System.getProperty("user.dir")+"/testData/Opencart_LoginData.xlsx";
		ExcelUtility xlutil = new ExcelUtility(path); //creating an object for XLUtility
		int totalrows=xlutil.getRowCount("Sheet 1");
		int totalcols=xlutil.getCellCount("Sheet 1", 1);
		
		String logindata[][] = new String[totalrows][totalcols]; //created for 2D array which takes rows and cols from excel file
		for(int i=1;i<=totalrows;i++)//1 //read the data frm xcel storing i
		{
			for(int j=0;j<totalcols;j++) //0 i is rows j is col
			{
				logindata[i-1][j] = xlutil.getCellData("Sheet 1", i, j); //1,0 array index starts from 0 so i-1 
				System.out.print(logindata[i - 1][j] + " | ");
			}
			  System.out.println();
		}	
		return logindata; //returning two dim array
	}
	
	//DataProvider2
	//DataProvider3
	//DataProvider4

}
