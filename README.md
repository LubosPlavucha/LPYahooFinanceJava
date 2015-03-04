# LPYahooFinanceJava

LPYahooFinanceJava is simple and useful Java interface to Yahoo Finance API, using YQL language. Currently, it contains implementation to retrieve exchange rates from Yahoo webservices. By implementing few lines of code, it is possible to get up-to-date exchange rates to all currencies. LPYahooFinanceJava is open source project and stable contributions are welcome.

<br />
##### Example - return last exchange rates for USD to given currencies:

```java
YahooFXManager manager = new YahooFXManager();
// add base currency
manager.baseCurrency = "USD";
// add currencies for which you would like to get exchange rates to base currency
List<String> currencies = new ArrayList<String>();
currencies.add("EUR");
currencies.add("JPY");
currencies.add("AUD");
String url = manager.buildYQLURLForLastExchangeRates();

// get response		
manager.getYQLResponse(url);	// returns map – keys are the currencies and values are the exchange rates
```
