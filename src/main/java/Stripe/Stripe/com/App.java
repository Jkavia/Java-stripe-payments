package Stripe.Stripe.com;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;

/**
 * Hello world!
 *
 */
public class App {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws StripeException {
		Stripe.apiKey = "sk_test_EQmuDW67hI3bI1YrM5zZmNSC";

		// create a customer
		// everytime you receive a request for payment check if the corresponding
		// customer has cus-id in DB
		// if not create a new customer
		// once the customer is created save his stripe customer ID, and his card ID to database and
		// re-use it everytime.
		//on front end ask customer which card he wishes to use
		// save the date of cust id creation in DB and then run a monthly batch to
		// deduct money from his account.
		
		//to create a new customer
		/*Map<String, Object> customerParameter = new HashMap<String, Object>();
		customerParameter.put("email", "d@gmail.com");
		Customer newCustomer = Customer.create(customerParameter);*/

		
		//to retrive an existing customer
		Customer a = Customer.retrieve("cus_DqcjuMDa4snbGa");
		
		
		
		// add his card details in a paramdata type

		Map<String, Object> cardParam = new HashMap<String, Object>();
		cardParam.put("number", "4111111111111111");
		cardParam.put("exp_month", "11");
		cardParam.put("exp_year", "2025");
		cardParam.put("cvc", "123");

		// add cardparams to token params

		Map<String, Object> tokenParam = new HashMap<String, Object>();
		tokenParam.put("card", cardParam);

		// create a token with token params

		Token token = Token.create(tokenParam);

		// create source(source means card for stripe) for fetched customer as this card, 
		//by accessing the id of
		// created token

		Map<String, Object> source = new HashMap<String, Object>();
		source.put("source", token.getId());

		a.getSources().create(source);

		Map<String, Object> chargeParam = new HashMap<String, Object>();
		chargeParam.put("amount", "500");
		chargeParam.put("currency", "usd");
		chargeParam.put("customer", a.getId());
		
		//to charge from a specific card
		/*chargeParam.put("source", newCustomer.getCards());
		*/
		Charge c = Charge.create(chargeParam);

		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// System.out.println(gson.toJson(a.getSources()));
		
		//code for retriving the card_ids from stripe.
		//System.out.println(a.getSources().getData().get(0).getId());
	}
}
