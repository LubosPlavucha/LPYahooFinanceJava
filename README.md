# LPYahooFinanceJava

LPYahooFinanceJava is simple and useful Java interface to Yahoo Finance API, using YQL language. Currently, it contains implementation to retrieve exchange rates from Yahoo webservices. By implementing few lines of code, it is possible to get up-to-date exchange rates to all currencies. LPYahooFinanceJava is open source project and stable contributions are welcome.

<br />
##### Example - return last exchange rates for USD to specified / all currencies:

```java
YahooFXManager manager = new YahooFXManager();
manager.baseCurrency = "USD";	// add base currency
manager.addCurrencies("EUR", "JPY", "AUD");	// add currencies for which you would like to get exchange rates to base currency
		
// get exchange rates for given currencies
Map<String, BigDecimal> exchangeRates = manager.getLastExchangeRates();	// returns map – keys are the currencies and values are the exchange rates
System.out.println(exchangeRates);
		
// get exchange rates for all currencies
manager.setAllAvailableCurrencies();
exchangeRates = manager.getLastExchangeRates();
System.out.println(exchangeRates);
```
