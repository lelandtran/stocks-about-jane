package stocks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

@Component
public class ScheduledTasks {
	
	@Scheduled(fixedRate = 5000)
	public void reportStockPrice(){
		try {
			String stockName = "INTC";
			Stock stock = YahooFinance.get("INTC");
			System.out.println("The stock price for " + stockName + " is " + stock.getQuote(true).getPrice());
		}
		catch (IOException e){
			System.out.println("IOException caught");
		}
	}

}