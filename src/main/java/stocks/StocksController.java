package stocks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;

@Controller
public class StocksController {

	protected static String stockSymbol = "INTC";
	protected static String phoneNumber = "+17608715513";

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
	public ResponseEntity<?> editStock(@RequestParam(value="stockParam", required=false) String stockParam) throws JSONException {
		JSONObject response = new JSONObject();
		ResponseEntity<String> resp = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		try {
			System.out.println("stockParam: " + stockParam);			
			if (stockParam != null){
				Stock stock = YahooFinance.get(stockParam.toUpperCase());
				if (stock != null){
					stockSymbol = stockParam.toUpperCase();
					response.put("status", "success");
					resp = new ResponseEntity<String>(response.toString(), HttpStatus.OK);
				}
				else {
					response.put("status", "error");
					response.put("errorMessage", "Could not find stock");
					resp = new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
				}
			}
			else {
				response.put("status", "error");
				response.put("errorMessage", "No stock specified");
				resp = new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
			}
		}
		catch (IOException ioe){
			response.put("status", "error");
			response.put("errorMessage", "Connection error");
			resp = new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
		}
		System.out.println("Returning response " + resp.toString());
		return resp;
	}

}
