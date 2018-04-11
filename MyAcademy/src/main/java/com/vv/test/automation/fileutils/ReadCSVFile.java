package com.vv.test.automation.fileutils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.base.Optional;

public class ReadCSVFile 
{
	static String csvPath = "";
	static List<Map<String, String>> csvData = new ArrayList<Map<String, String>>();
	static ResultSet resultset = null;

	/**
	 * @author Cigniti
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String,String>> getData(String path) throws IOException 
	{
		List<Map<String,String>> allresult = new ArrayList<Map<String,String>>();
		String csvFile = path;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try 
		{
			br = new BufferedReader(new FileReader(csvFile));
			Map<String,String> results;
			while ((line = br.readLine())!= null) 
			{
				results = new HashMap<String,String>();
				String[] res = line.split(cvsSplitBy);
				results.put("TestName", res[0]);
				results.put("JiraTCID", res[1]);
				results.put("Status", res[2]);
				results.put("Machine", res[3]);
				results.put("Owner", res[4]);
				results.put("Description", res[5]);
				allresult.add(results);
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			if (br != null) 
			{
				try 
				{
					br.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return allresult;
	}

	/**
	 * @author Cigniti
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String,String>> getJiraCSVData(String path) throws IOException 
	{
		List<Map<String,String>> allresult = new ArrayList<Map<String,String>>();
		String csvFile = path;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try 
		{
			br = new BufferedReader(new FileReader(csvFile));
			Map<String,String> results;
			while ((line = br.readLine())!= null) 
			{
				results = new HashMap<String,String>();
				String[] res = line.split(cvsSplitBy);
				if(!res[0].contains("TCM"))
					continue;
				results.put("JiraTCID", res[0]);
				results.put("ModuleName", res[1]);
				results.put("ClassName", res[2]);
				results.put("MethodName", res[3]);
				results.put("Owner", res[4]);
				results.put("Environment", res[5]);
				results.put("TestCaseType", res[6]);
				results.put("Testing_Type", res[7]);
				results.put("Active", res[8]);
				allresult.add(results);
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			if (br != null) 
			{
				try 
				{
					br.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return allresult;
	}
	
	/**
	 * @author Cigniti
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public List<Map<String,String>> getCSVData_Execution(String path) throws IOException 
	{
		List<Map<String,String>> allresult = new ArrayList<Map<String,String>>();
		String csvFile = path;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try 
		{
			br = new BufferedReader(new FileReader(csvFile));
			Map<String,String> results;
			while ((line = br.readLine())!=null) 
			{  results = new HashMap<String,String>();
				String[] res = line.split(cvsSplitBy);
				if(res[0].contains("ModuleName"))
					continue;
				results.put("module", res[0]);
				results.put("testingtype", res[1]);
				results.put("environment", res[2]);
				results.put("methodname", res[3]);
				allresult.add(results);
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			if (br != null) 
			{
				try 
				{
					br.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return allresult;
	}

	public static int getPassCount(List<Map<String,String>> res)
	{
		return getSimilarRecordCount("Status","Pass",res);
	}
	
	public static int getSimilarRecordCount(String columnName, String columnValue, List<Map<String,String>> res){
		int counter = 0;
		try
		{
			for (Map<String, String> map : res) 
			{
				String status = map.get(columnName);
				if(status.equalsIgnoreCase(columnValue))
				{
					counter++;
				}
			}
		}
		catch(Exception e)
		{
			return -1;
		}
		return counter;
	}
	
	/**
	 * @author Cigniti
	 * @param resultfileCompletePath
	 * @return
	 */
	public static List<Map<String,String>> getAllFailedTestsDetail(String resultfileCompletePath)
	{
		List<Map<String, String>> res1 = new ArrayList<Map<String, String>>();
		Map<String, String> map1 = new HashMap<String,String>();
		try
		{
			map1.put("Status", "%AIL");
			res1 = GetCSVData(map1, resultfileCompletePath);
		}
		catch(Exception e)
		{
			res1=null;
			e.printStackTrace();
		}
		return res1;
	}

	/**
	 * @author Cigniti
	 * @param fileFullyQualifiedPath
	 * @return
	 */
	public static List<Map<String,String>> getAllFailedAndSkippedTestsDetail(String fileFullyQualifiedPath)
	{
		List<Map<String, String>> res1 = new ArrayList<Map<String, String>>();
		Map<String, String> map1 = new HashMap<String,String>();
		try
		{
			map1.put("Status", "%I%");
			res1 = GetCSVData(map1, fileFullyQualifiedPath);
		}
		catch(Exception e)
		{
			res1=null;
			e.printStackTrace();
		}
		return res1;
	}

	/**
	 * @author Cigniti
	 * @param fileFullyQualifiedPath
	 * @return
	 */
	public static List<Map<String,String>> getAllSkippedTestsDetail(String fileFullyQualifiedPath)
	{
		List<Map<String, String>> res1 = new ArrayList<Map<String, String>>();
		Map<String, String> map1 = new HashMap<String,String>();
		try
		{
			map1.put("Status", "%KIP");
			res1 = GetCSVData(map1, fileFullyQualifiedPath);
		}
		catch(Exception e)
		{
			res1=null;
			e.printStackTrace();
		}
		return res1;
	}

	public static void createFileContainsFailedTC(String resultfileCompletePath, Optional<String> pathToSaveFailedTCDetail){
		String failedFilePath = "";
		if(pathToSaveFailedTCDetail.isPresent()){
			failedFilePath = pathToSaveFailedTCDetail.get();
		}
		else{
			failedFilePath = resultfileCompletePath.substring(0, resultfileCompletePath.lastIndexOf("."));
			failedFilePath = failedFilePath+"_Failed.xlsx";
		}
		
		writeDataInExcelFile(getAllFailedTestsDetail(resultfileCompletePath),failedFilePath);
	}
	
	/**
	 * @author Cigniti
	 * @param resultfileCompletePath
	 * @param pathToSaveSelectedTCDetail
	 */
	public static void createFileContainsFailedAndSkipTC(String resultfileCompletePath, Optional<String> pathToSaveSelectedTCDetail){
		String failedFilePath = "";
		if(pathToSaveSelectedTCDetail.isPresent()){
			failedFilePath = pathToSaveSelectedTCDetail.get();
		}
		else{
			failedFilePath = resultfileCompletePath.substring(0, resultfileCompletePath.lastIndexOf("."));
			failedFilePath = failedFilePath+"_Failed_Skipped.xlsx";
		}
		
		writeDataInExcelFile(getAllFailedAndSkippedTestsDetail(resultfileCompletePath),failedFilePath);
	}
	
	/**
	 * @author Cigniti
	 * @param res
	 * @param path
	 * @return
	 */
	public static boolean writeDataInExcelFile(List<Map<String, String>> res, String path){
        try
        {
        	//Blank workbook
        	XSSFWorkbook workbook = new XSSFWorkbook();
        	//Create a blank sheet
        	XSSFSheet sheet = workbook.createSheet("AutomationExecutionResult");
        	//This data needs to be written (Object[])
        	Map<String, String> data = res.get(0);
        	//Iterate over data and write to sheet
        	Set<String> keyset = data.keySet();
        	int rownum = 0;
        	int cellnum = 0;
        	Row topheader= sheet.createRow(rownum++);
        	for(String key : keyset){
        		Cell cell = topheader.createCell(cellnum++);
    			cell.setCellValue(key);
        	}
        	for (Map<String, String> rowData : res)
        	{
        		Row row = sheet.createRow(rownum++);
        		cellnum = 0;
        		for (String key : keyset)
        		{
        			Cell cell = row.createCell(cellnum++);
        			cell.setCellValue(rowData.get(key));
        		}
        	}
        	//Write the workbook in file system
        	FileOutputStream out = new FileOutputStream(new File(path));
        	workbook.write(out);
        	out.close();
        	workbook.close();
        	System.out.println(path+" file saved successfully on disk.");
        	return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return false;
	}

	/**
	 * @author Cigniti
	 * @param searchCriteria
	 * @param fileFullyQualifiedPath
	 * @return
	 */
	public static List<Map<String, String>> GetCSVData(Map<String, String> searchCriteria, String fileFullyQualifiedPath)
	{
		csvPath = fileFullyQualifiedPath;
		String[] fileName = fileFullyQualifiedPath.split("/");
		csvData.clear();
		String query = "Select * from "+fileName[fileName.length-1];
		query = query.substring(0, query.lastIndexOf(".csv"));
		try{
			if(searchCriteria!=null && searchCriteria.size()>0){
				query = query+" where ";
				Set<String> keysValue = searchCriteria.keySet();
				for(String keyValue: keysValue){
					query = query + " "+keyValue+" like '"+searchCriteria.get(keyValue)+"' and";
				}
				query  =query.substring(0, query.lastIndexOf("and"));
			}
			String filePath = fileFullyQualifiedPath.substring(0, fileFullyQualifiedPath.lastIndexOf("/"));
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			Connection conn = DriverManager.getConnection("jdbc:relique:csv:" +
					filePath);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			resultset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = resultset.getMetaData();
			while (resultset.next()) {
				Map<String,String> dataRow = new HashMap<String, String>();
				for (Integer i = rsmd.getColumnCount(); i >= 1 ; i--) {
					dataRow.put(rsmd.getColumnName(i).toString().replace(" ", "_"), resultset.getString(i).toString());
				}
				csvData.add(dataRow);
			}
			stmt.close();
			conn.close();
		}
		catch(Exception e){
			System.out.println("Failed to get CSV data at '"+fileFullyQualifiedPath+"' location "
					+ "based on  provided search criteria, if any.");
		}
		return csvData;
	}

	/**
	 * @author ashish.jain
	 */
	public static List<String> readCSVFile(String csvFilePath){ 
		  BufferedReader br = null;  
		  String line = "";  
		  String splitBy = ",";  
		  List<String> list=new ArrayList<String>();
		  try {  
		  
		   br = new BufferedReader(new FileReader(csvFilePath));  
		   int counter = 0;
		   while ((line = br.readLine()) != null) {  
		  
			    String[] data = line.split(splitBy);
			    System.out.println(Arrays.toString(data));  
			    list.add(Arrays.toString(data));
			   // System.out.println(list.get(counter));
			    counter++;
		   }  
		  } catch (FileNotFoundException e) {  
		   e.printStackTrace();  
		  } catch (IOException e) {  
		   e.printStackTrace();  
		  } finally {  
		   if (br != null) {  
		    try {  
		     br.close();  
		    } catch (IOException e) {  
		     e.printStackTrace();  
		    }  
		   }  
		  }  
		  return list;
	}
	
	/**
	 * @author Cigniti
	 * @description : Load csv data
	 * @param csvpath
	 * @return
	 */
	public List<Object[]> loadcsvdata(String csvpath){
		String csvFileToRead = csvpath;
		BufferedReader br = null;
		String line = "";
		String splitBy = ";";
		@SuppressWarnings("unused")
		int csvrowcnt=0;
		List<Object[]> csvData=  new ArrayList<Object[]>();
		try {
			csvrowcnt = getcsvrowcount(csvpath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Object[] lstDataRow;
		try {
			br = new BufferedReader(new FileReader(csvFileToRead));
			br.readLine();
			while ((line = br.readLine()) != null) {
				lstDataRow = line.split(splitBy);
				csvData.add(lstDataRow);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return csvData;
	}

	/**
	 * @author Cigniti
	 * @description : Get csv count
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public int getcsvrowcount(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}
	
	/**
	 * @author Cigniti
	 * @description: get list of IP's of all grid nodes connected to hub
	 * @return
	 */
	public static List<Map<String, String>> getListOfData(String csvFilePath)
	{
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		List<Map<String, String>> ips=new ArrayList<>();

		try {

			br = new BufferedReader(new FileReader(csvFilePath));
			while ((line = br.readLine()) != null) {
				Map<String, String> ip=new HashMap<>();
				String[] row = line.split(cvsSplitBy);
				ip.put("ip", row[0]);
				ip.put("platform", row[1]);
				ips.add(ip);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {}
			}
		}
		return ips;
	}

}