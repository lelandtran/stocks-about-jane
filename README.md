# stocks-about-jane #

###How to operate###
```mvn clean install```.
```java -jar target/stocks-about-jane-0.1.0.jar```

###Expected Output###
Each morning, a scheduled method will run to retrieve Jane's initial stock price for the day. Each hour, another scheduled method will check if the stock price has changed by a certain "deltaToNotify" variable defined in ScheduledStockCheck.java. If it has, she would theoretically receive a text. However, my Twilio trial account has already expired so I could not get a working phone number for free.

Additionally, a simple web application will be deployed on http://localhost:8080 that displays the stock symbol and price for Jane's stock. If she would like to edit the stock watched, she need just to enter in the new stock's symbol and the "Edit" button next to the textbox labeled "Stock: ".

###Design Principles and Algorithms###
I started with using the Spring MVC framework because it is what I'm most experienced in. I used the MVC framework to implement the website portion of the application where the model was a stock symbol and price pair, the view was a simple layout to display the stock information as well as a form to edit which the watched stock, and the controller handled.
The Spring framework also features an @Scheduled annotation that will run a particular method in a Spring Component at a regular schedule which is how I called the Yahoo Finance API regularly.

###Next Steps###
Some features that I wanted to pursue but did not have time for included:
*Allowing Jane to configure how often she wanted price checks called
*Allowing Jane to configure her phone number
*Allowing Jane to have multiple stocks on her Stock WatchList
*Expanding this to a web application that Jane could invite her friends to use