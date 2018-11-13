# chinese-takeaway
Spring REST with MongoDb. Work in progress (feature-stats).

Chinese Takeaway
Back-end API to order and process Chinese takeaway

Introduction
Everybody loves a good Chinese takeaway. And we all follow the exact same pattern to order Chinese. We go to their website, look for something we like and call them to place our order. When we do, we don’t tell them the entire name of the dish we would like to order, but we only tell them the number. With a proper frontend, Hu Awai, the person answering the phone to take the order would be able to fill in your order based on the numbers you tell him, see what the number stands for and send your entire order to So Ni, the cook in the kitchen.

Building the API
The backend will be build using Spring Boot 2 and MongoDB, using the proper methodologies. What is required, is written down in the following sections.

Store and retrieve
For Hu Awai to be able to send an order to the kitchen, there should be a collection of meals you can order. The first step is to provide the CRUD REST calls so one is able to enter meals and fill in the collection. You are free to choose the fields a meal should contain, but don’t forget the typical meal number, name, and possible allergies (gluten, lactose, etc.)

Taking orders
At this step, Hu Awai can already see a list of all the meals and look for a specific meal based on the menu number. The next step will be for Hu Awai to be able to make orders. When Hu answers the phone he will open up the program, fill in a name and a list of meal items the customer desires. When he clicks the virtual “Send to kitchen” button, he will retrieve an answer from the system, confirming the complete order, showing the real meals and not the numbers he entered, the status REQUESTED and showing the time the order will be ready. For now, the completion time is the time of ordering plus 30 minutes.

The cook
After Hu submitted the order, So will retrieve the new order at the bottom of her order list. She will retrieve the order list based on ascending creation date with the status REQUESTED or PREPARING. Make sure there is a rest path to get that list. So now takes the next requested order on top and starts making the meal, setting the status of the order to PREPARING. When she is ready, she will need to update the order by setting the status from PREPARING to DONE.

The status
A customer who placed an order enters the takeaway and provides his name. Hu must be able to search the orders by the person’s name and tell him the current status. Only when the order is set to DONE by the kitchen, the person can take the order at home and So will mark the order with status COLLECTED.

Allergies
A customer walks in the takeaway and asks Hu a list of meals that do not contain lactose. Hu is very delighted because his wonderful new system has a REST call for doing that. In no time, Hu gets the list and tells the customer which meals remain.

Statistics
At the end of the month, Hu and So evaluate their business and want to know which meals are the most favourite of their clients. That way they can optimise their stock and reduces their ecological footprint. Make sure the system can deliver this information using a REST call.

Recent activity 
Finished refactoring the stats feature.
Allergies needs refactoring.
Plans to move to Spring Data REST.
