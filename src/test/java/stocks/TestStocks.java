package stocks;

import junit.framework.Assert;
import org.junit.Test;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;


public class TestStocks {
	
	// I apologize for the sparse number of tests. I would have written more had time permitted
	@Test
	public void testStockPriceCheck() throws IOException {
		Assert.assertNotNull(YahooFinance.get("INTC"));
	}
}