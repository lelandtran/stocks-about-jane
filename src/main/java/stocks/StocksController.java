package stocks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;

@Controller
public class StocksController {

	private static String stockSymbol = "INTC";
	private static String phoneNumber = "+17608715513";

	@RequestMapping("/")
	public String home(Model model){
		try {
			Stock stock = YahooFinance.get(stockSymbol);
			model.addAttribute("stockSymbol", stockSymbol);
			model.addAttribute("stockPrice", stock.getQuote(true).getPrice());
		}
		catch (IOException e){

		}
		return "index";
	}

	@RequestMapping("/stock/edit")
	public JSONObject editStock(@RequestParam(value="stockParam", required=false) String stockParam) throws JSONException {
		JSONObject response = new JSONObject();
		try {			
			if (stockParam != null){
				Stock stock = YahooFinance.get(stockParam.toUpperCase());
				if (stock != null){
					stockSymbol = stockParam.toUpperCase();
					response.put("status", "success");
				}
				else {
					response.put("status", "error");
					response.put("errorMessage", "Could not find stock");
				}
			}
		}
		catch (IOException ioe){
			response.put("status", "error");
			response.put("errorMessage", "Connection error");
		}
		return response;
	}

	@RequestMapping("/phone/edit")
	public JSONObject editPhone(@RequestParam(value="phoneParam", required=false) String phoneParam) throws JSONException {
		JSONObject response = new JSONObject();
		if (phoneParam != null){
			String phoneRegex = "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";
			if (phoneParam.matches(phoneRegex)){
				phoneNumber = phoneParam;
				response.put("status", "success");
			}
			else {
				response.put("status", "error");
				response.put("errorMessage", "Phone format not valid");
			}
		}
		return response;
	}

}
