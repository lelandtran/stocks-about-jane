package stocks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledStockCheck {

	private static BigDecimal startingPriceOfToday = new BigDecimal(BigDecimal.ROUND_CEILING);
	private static double deltaToNotify = 1.0;

	private static final String ACCOUNT_SID = "AC6e71fb7422800a2cc39230311da08588";
	private static final String AUTH_TOKEN = "8054f0218fc92eaed7f4d9e22e3cea01";
	
	@Scheduled(cron = "0 0 8 * * *", zone = "EST")
	public void setStartingPriceOfToday(){
		try {
			Stock stock = YahooFinance.get(StocksController.stockSymbol);
			startingPriceOfToday = stock.getQuote(true).getPrice();
			textUser(startingPriceOfToday.doubleValue());
		}
		catch (IOException e){
			System.out.println("IOException caught");
		}

	}

	//@Scheduled(cron = "*/30 * * * * *") // for test purposes, check every 30 seconds
	@Scheduled(cron = "0 0 * * * *")
	public void reportStockPriceHourly(){
		try {
			Stock stock = YahooFinance.get(StocksController.stockSymbol);
			BigDecimal currentPrice = stock.getQuote(true).getPrice();
			if (currentPrice.subtract(startingPriceOfToday).doubleValue() > deltaToNotify){
				textUser(currentPrice.doubleValue());
			}
			System.out.println("The stock price for " + StocksController.stockSymbol + " is " + stock.getQuote(true).getPrice());
		}
		catch (IOException e){
			System.out.println("IOException caught");
		}
	}



	public static void textUser(double currentPrice){
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("To", StocksController.phoneNumber));
		params.add(new BasicNameValuePair("From", "+17608715513"));
		StringBuilder sb = new StringBuilder();
		sb.append("Hi Jane! Your stock in ").append(StocksController.stockSymbol).append("is currently at ").append(currentPrice);
		String textMessage = sb.toString();
		params.add(new BasicNameValuePair("Body", textMessage));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		//try { Message message = messageFactory.create(params); } catch (TwilioRestException e) { e.printStackTrace(); }
		System.out.println("Text sent to Jane: " + "\n\t" + textMessage);
		

	}

}