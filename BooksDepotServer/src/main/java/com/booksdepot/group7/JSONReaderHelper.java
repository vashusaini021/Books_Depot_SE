package com.booksdepot.group7;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import com.booksdepot.group7.models.BooksModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSONReaderHelper {

	
	
	private static JSONReaderHelper instance = null;
	public ArrayList<BooksModel> booksList = new ArrayList<BooksModel>();

	private JSONReaderHelper()
	{
		try {
			LoadJsonDataForInitialization();

		} catch (Exception e) {
			System.out.println("Error in reading json file-->> "+ e.getLocalizedMessage());
		}
	}

	public static JSONReaderHelper getInstance()
	{
		if (instance == null)
			instance = new JSONReaderHelper();

		return instance;
	}

	private void LoadJsonDataForInitialization() throws IOException { 
		ClassPathResource staticDataResource = new ClassPathResource("static/books.json");
		String staticDataString = IOUtils.toString(staticDataResource.getInputStream(), StandardCharsets.UTF_8);

		Map<String, Object> jsonObj =  new JSONObject(staticDataString).toMap();
		List<Map<String, Object>> books = (List<Map<String, Object>>) jsonObj.get("books");

		for (Map<String, Object> element : books) {
			String title =  element.get("title").toString();
			String isbn =  (element.containsKey("isbn")) ? element.get("isbn").toString() : "";
			int pageCount =  Integer.parseInt(element.get("pageCount").toString());        	
			String publishedDate =  (element.containsKey("publishedDate")) ? element.get("publishedDate").toString() : "";
			String thumbnailUrl =  (element.containsKey("thumbnailUrl")) ? element.get("thumbnailUrl").toString() : "";
			String shortDescription =  (element.containsKey("shortDescription")) ? element.get("shortDescription").toString() : "";
			String longDescription =  (element.containsKey("longDescription")) ? element.get("longDescription").toString() : "";
			String status =  (element.containsKey("status")) ? element.get("status").toString() : "";
			String price =  (element.containsKey("price")) ? element.get("price").toString() : "";
			boolean isFeatured =  (element.containsKey("isFeatured")) ? (boolean)element.get("isFeatured") : false;
			boolean isBestSeller =  (element.containsKey("isBestSeller")) ? (boolean)element.get("isBestSeller") : false;
			String[] authors =  (element.containsKey("status")) ? ((ArrayList<String>) element.get("authors")).toArray(new String[0]) : null;
			String[] categories =  (element.containsKey("categories")) ? ((ArrayList<String>) element.get("categories")).toArray(new String[0]) : null;

			BooksModel newObj = new BooksModel(title, isbn, pageCount, publishedDate, thumbnailUrl, shortDescription, longDescription, status,authors , categories, price, isFeatured, isBestSeller);
			booksList.add(newObj);
		}

		System.out.println("Mock Data Made Successfully");
	}


}
