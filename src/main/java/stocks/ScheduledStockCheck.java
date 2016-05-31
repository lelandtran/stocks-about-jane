package stocks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

@Component
public class ScheduledStockCheck {

	private static BigDecimal startingPriceOfToday = new BigDecimal(BigDecimal.ROUND_CEILING);
	private static double deltaToNotify = 1.0;
	private static String stockName = "INTC";
	
	@Scheduled(cron = "0 0 8 * * *", zone = "EST")
	public void setStartingPriceOfToday(){
		try {
			Stock stock = YahooFinance.get(stockName);
			startingPriceOfToday = stock.getQuote(true).getPrice();
		}
		catch (IOException e){
			System.out.println("IOException caught");
		}

	}

	@Scheduled(cron = "0 0 0 * * *")
	public void reportStockPriceHourly(){
		try {
			Stock stock = YahooFinance.get(stockName);
			BigDecimal currentPrice = stock.getQuote(true).getPrice();
			if (currentPrice.subtract(startingPriceOfToday).doubleValue() > deltaToNotify){
				textUser(currentPrice.doubleValue());
			}
			System.out.println("The stock price for " + stockName + " is " + stock.getQuote(true).getPrice());
		}
		catch (IOException e){
			System.out.println("IOException caught");
		}
	}

	public static void textUser(double currentPrice){
		// call Twilio API
	}

}