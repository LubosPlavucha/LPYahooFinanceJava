package com.lubosplavucha.lpyahoofinance;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


// TODO catch all possible errors that can occur


public class YahooFXManager {
	
	
	private String baseCurrency;
	private SortedSet<String> currencies = new TreeSet<String>();	// Use set to avoid duplicates
	
	
	public Map<String, BigDecimal> getLastExchangeRates() throws Exception {
		if(baseCurrency == null || currencies.isEmpty())
			throw new IllegalStateException();
		
		return getYQLResponse(buildYQLURLForLastExchangeRates());
	}
	
	
	public Map<String, BigDecimal> getHistoricalExchangeRates(Date beginDate, Date endDate) throws Exception {
		if(baseCurrency == null || currencies.isEmpty())
			throw new IllegalStateException();
		
		// TODO
		
		return null;
	}
	
	
	private Map<String, BigDecimal> getYQLResponse(String yahooURL) throws Exception {
		
		Map<String, BigDecimal> exchangeRates = new HashMap<String, BigDecimal>();
		
		InputStream inputStream = null;
		
		try {
			
			URL url = new URL(yahooURL);
			URLConnection connection = url.openConnection();
			connection.setDoInput(true); // true if we want to read server's response
			connection.setDoOutput(false); // false indicates this is a GET request
			inputStream = connection.getInputStream();
			
			// create a new DocumentBuilderFactory
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();	// use the factory to create a documentbuilder
			Document doc = builder.parse(connection.getInputStream());
			
			NodeList nodes = doc.getElementsByTagName("results");
			Element element = (Element) nodes.item(0);
	        NodeList ratesList = element.getElementsByTagName("rate");
			
	        for(int a = 0; a < ratesList.getLength(); a++) {
				
	        	Element rateElement = (Element) ratesList.item(a);
				String id = rateElement.getAttribute("id");
				Element exchangeRateElement = (Element)rateElement.getElementsByTagName("Rate").item(0);
				
				if(id.length() == 6) {
					String currency = id.substring(3, 6);
					BigDecimal exchangeRate = new BigDecimal(exchangeRateElement.getTextContent());
					exchangeRates.put(currency, exchangeRate);
				}
			}
	        
		} finally {
			if(inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return exchangeRates;
	}

	
	private String buildYQLURLForLastExchangeRates() throws UnsupportedEncodingException {
		
		StringBuilder urlBuilder = new StringBuilder("http://query.yahooapis.com/v1/public/yql?q=");
		urlBuilder.append(URLEncoder.encode("select * from yahoo.finance.xchange where pair in", "UTF-8"));	// empty spaces need to be encoded
		
		// add currencies
		urlBuilder.append("(");
		String prefix = "";
		for(String currencyCode: this.currencies) {
			urlBuilder.append(prefix);
			prefix = ",";
			urlBuilder.append("'" + baseCurrency + currencyCode + "'");
		}
		urlBuilder.append(")&env=store://datatables.org/alltableswithkeys");
				
		return urlBuilder.toString();
	}
	
	
	public void setBaseCurrency(String baseCurrency) {
		if (baseCurrency.length() != 3)
			return;
		this.baseCurrency = baseCurrency;
	}
	
	
	public void addCurrency(String currency) {
		if (currency.length() != 3)
			return;
		this.currencies.add(currency);
	}
	
	
	public void addCurrencies(String ... currencies) {
		for(String currency: currencies) {
			if (currency.length() != 3)
				continue;
			this.currencies.add(currency);
		}
	}
	
	
	/* May contain obsolete currencies. */
	public void setAllAvailableCurrencies() {
		for(Currency currency: Currency.getAvailableCurrencies()) {
			this.currencies.add(currency.getCurrencyCode());
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		YahooFXManager manager = new YahooFXManager();
		manager.baseCurrency = "USD";   // add base currency
		manager.addCurrencies("EUR", "JPY", "AUD"); // add currencies for which you would like to get exchange rates to base currency

		// get exchange rates for given currencies
		Map<String, BigDecimal> exchangeRates = manager.getLastExchangeRates(); // returns map – keys are the currencies and values are the exchange rates
		System.out.println(exchangeRates);

		// get exchange rates for all currencies
		manager.setAllAvailableCurrencies();
		exchangeRates = manager.getLastExchangeRates();
		System.out.println(exchangeRates);
	}
}