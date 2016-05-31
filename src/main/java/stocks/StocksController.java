package stocks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

@Controller
public class StocksController {

	@RequestMapping("/helloworld")
	public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model){
		model.addAttribute("name", name);
		return "greeting";
	}

	@RequestMapping("/")
	public String index(Model model){
		try {
			Stock stock = YahooFinance.get("INTC");
			model.addAttribute("name", "INTC");
			model.addAttribute("price", stock.getQuote(true).getPrice());
		}
		catch (IOException e){

		}
		return "index";
	}

}
