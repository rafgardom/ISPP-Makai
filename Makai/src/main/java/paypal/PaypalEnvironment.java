
package paypal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;

public class PaypalEnvironment {

	// Replace with your application client ID and secret
	String						clientId		= "AT2FM6lWhqSaCllksuML3kuqf0ZZgW0sWgcSmAFejFBa-Ysdrw9oAhV6ejngMfeI-PirlCdVyLOGZsN4";
	String						clientSecret	= "EBHkYjQTtg36crx7aWag7HBi4iYSJJyKcInS27MvbWwFN1SJJOVQuU87AQ4HG3PRCSPw58NO4OpY8bw3";

	APIContext					apiContext		= new APIContext(this.clientId, this.clientSecret, "live");
	Map<String, String>			map				= new HashMap<String, String>();

	private static final Logger	LOGGER			= Logger.getLogger(PaypalEnvironment.class);


	public Payment createPayment() {
		// Set payer details
		final Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		// Set redirect URLs
		final RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost:3000/cancel");
		redirectUrls.setReturnUrl("http://localhost:3000/process");

		// Set payment details
		final Details details = new Details();
		details.setShipping("1");
		details.setSubtotal("5");
		details.setTax("1");

		// Payment amount
		final Amount amount = new Amount();
		amount.setCurrency("USD");
		// Total must be equal to sum of shipping, tax and subtotal.
		amount.setTotal("7");
		amount.setDetails(details);

		// Transaction information
		final Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("This is the payment transaction description.");

		// Add transaction to a list
		final List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// Add payment details
		final Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setRedirectUrls(redirectUrls);
		payment.setTransactions(transactions);

		// Create payment
		try {
			final Payment createdPayment = payment.create(this.apiContext);

			final Iterator links = createdPayment.getLinks().iterator();
			while (links.hasNext()) {
				final Links link = (Links) links.next();
				if (link.getRel().equalsIgnoreCase("approval_url"))
					// REDIRECT USER TO link.getHref()
					System.out.println(link.getHref());
			}
		} catch (final PayPalRESTException e) {
			System.err.println(e.getDetails());
		}

		return payment;
	}

	public void confirmationPayment(final HttpServletRequest req) {
		final Payment payment = new Payment();
		payment.setId(req.getParameter("paymentId"));

		final PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(req.getParameter("PayerID"));
		try {
			final Payment createdPayment = payment.execute(this.apiContext, paymentExecution);
			System.out.println(createdPayment);
		} catch (final PayPalRESTException e) {
			System.err.println(e.getDetails());
		}
	}

	public static double round(double value, final int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		final long factor = (long) Math.pow(10, places);
		value = value * factor;
		final long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public Payment createPayment(final HttpServletRequest req, final HttpServletResponse resp, final double totalAmount, final int objectId, final String objectType, final String controllerPath) {
		Payment createdPayment = null;

		// ### Api Context
		// Pass in a `ApiContext` object to authenticate
		// the call and to send a unique request id
		// (that ensures idempotency). The SDK generates
		// a request id if you do not pass one explicitly.
		if (req.getParameter("PayerID") != null) {
			final Payment payment = new Payment();
			if (req.getParameter("guid") != null)
				payment.setId(this.map.get(req.getParameter("guid")));

			final PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(req.getParameter("PayerID"));
			try {

				createdPayment = payment.execute(this.apiContext, paymentExecution);
				ResultPrinter.addResult(req, resp, "Executed The Payment", PayPalResource.getLastRequest(), PayPalResource.getLastResponse(), null);
			} catch (final PayPalRESTException e) {
				ResultPrinter.addResult(req, resp, "Executed The Payment", PayPalResource.getLastRequest(), null, e.getMessage());
			}
		} else {

			// ###Details
			// Let's you specify details of a payment amount.
			//			final Details details = new Details();
			//			details.setShipping("1");
			//			details.setSubtotal("5");
			//			details.setTax("1");

			// ###Amount
			// Let's you specify a payment amount.
			final Amount amount = new Amount();
			amount.setCurrency("EUR");
			// Total must be equal to sum of shipping, tax and subtotal.
			amount.setTotal("7");
			//###Makai's commission
			final Double finalPrice = PaypalEnvironment.round(totalAmount, 2);
			amount.setTotal(finalPrice.toString());
			//			amount.setDetails(details);

			// ###Transaction
			// A transaction defines the contract of a
			// payment - what is the payment for and who
			// is fulfilling it. Transaction is created with
			// a `Payee` and `Amount` types
			final Transaction transaction = new Transaction();

			transaction.setAmount(amount);
			transaction.setDescription("This is the payment transaction description.");

			// ### Items
			final Item item = new Item();
			item.setName("Training/Entrenamiento").setQuantity("1").setCurrency("EUR").setPrice(finalPrice.toString());
			final ItemList itemList = new ItemList();
			final List<Item> items = new ArrayList<Item>();
			items.add(item);
			itemList.setItems(items);

			transaction.setItemList(itemList);

			// The Payment creation API requires a list of
			// Transaction; add the created `Transaction`
			// to a List
			final List<Transaction> transactions = new ArrayList<Transaction>();
			transactions.add(transaction);

			// ###Payer
			// A resource representing a Payer that funds a payment
			// Payment Method
			// as 'paypal'
			final Payer payer = new Payer();
			payer.setPaymentMethod("paypal");

			// ###Payment
			// A Payment Resource; create one using
			// the above types and intent as 'sale'
			final Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);

			// ###Redirect URLs
			final RedirectUrls redirectUrls = new RedirectUrls();
			final String guid = UUID.randomUUID().toString().replaceAll("-", "");
			redirectUrls.setCancelUrl(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/");
			redirectUrls.setReturnUrl(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + controllerPath + "/payment/done.do?guid=" + guid + "&" + objectType + "=" + objectId);
			payment.setRedirectUrls(redirectUrls);

			// Create a payment by posting to the APIService
			// using a valid AccessToken
			// The return object contains the status;
			try {
				createdPayment = payment.create(this.apiContext);
				PaypalEnvironment.LOGGER.info("Created payment with id = " + createdPayment.getId() + " and status = " + createdPayment.getState());
				// ###Payment Approval Url
				final Iterator<Links> links = createdPayment.getLinks().iterator();
				while (links.hasNext()) {
					final Links link = links.next();
					if (link.getRel().equalsIgnoreCase("approval_url"))
						req.setAttribute("redirectURL", link.getHref());
				}
				ResultPrinter.addResult(req, resp, "Payment with PayPal", PayPalResource.getLastRequest(), PayPalResource.getLastResponse(), null);
				this.map.put(guid, createdPayment.getId());
			} catch (final PayPalRESTException e) {
				ResultPrinter.addResult(req, resp, "Payment with PayPal", PayPalResource.getLastRequest(), null, e.getMessage());
			}
		}
		return createdPayment;
	}
}
