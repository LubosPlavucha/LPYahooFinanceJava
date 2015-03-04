package com.lubosplavucha.lpyahoofinance;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;


public class YahooFXManager {
	
	
	private String baseCurrency;
	private SortedSet<String> currencies = new TreeSet<String>();
	
	
	public String getBaseCurrency() {
		return baseCurrency;
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
	
	
	public void addCurrencies(Collection<String> currencies) {
		for(String currency: currencies) {
		if (currency.length() != 3)
			continue;
			this.currencies.add(currency);
		}
	}
}