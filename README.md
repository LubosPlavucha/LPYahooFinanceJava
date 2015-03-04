
Examples:

    // test YQL URL building
		YahooFXManager manager = new YahooFXManager();
		manager.baseCurrency = "USD";
		List<String> currencies = new ArrayList<String>();
		currencies.add("EUR");
		currencies.add("JPY");
		currencies.add("AUD");
		String url = manager.buildYQLURLForLastExchangeRates();
		System.out.println(url);
		
		// test YQL response
		System.out.println(manager.getYQLResponse(url));
