
package paypal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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

public class PaymentWithPayPalServlet extends HttpServlet {

	private static final long	serialVersionUID	= 1L;
	private final String		clientID			= "AVeBFGHYN5KimXYs0YcPfQyK1WSEMuAt2T2KJU3cmstzoxv1tnwBuBM2iYdB4M-uWOl3S3H_DNEWtADJ";
	private final String		clientSecret		= "EDriyWEyd9NkgJuVnyavMn5qmBXZk4DfsCLeUUKCr1WvRGobKzIEqIosgNPQXxgUSGdfRja_hAsyWexv";
	private final String		mode				= "live";

	private static final Logger	LOGGER				= Logger.getLogger(PaymentWithPayPalServlet.class);
	Map<String, String>			map					= new HashMap<String, String>();


	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	// ##Create
	// Sample showing to create a Payment using PayPal
	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		this.createPayment(req, resp);
		req.getRequestDispatcher("response.jsp").forward(req, resp);
	}

	public Payment createPayment(final HttpServletRequest req, final HttpServletResponse resp) {
		Payment createdPayment = null;

		// ### Api Context
		// Pass in a `ApiContext` object to authenticate
		// the call and to send a unique request id
		// (that ensures idempotency). The SDK generates
		// a request id if you do not pass one explicitly.

		final APIContext apiContext = new APIContext(this.clientID, this.clientSecret, this.mode);
		if (req.getParameter("PayerID") != null) {
			final Payment payment = new Payment();
			if (req.getParameter("guid") != null)
				payment.setId(this.map.get(req.getParameter("guid")));

			final PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(req.getParameter("PayerID"));
			try {

				createdPayment = payment.execute(apiContext, paymentExecution);
				ResultPrinter.addResult(req, resp, "Executed The Payment", PayPalResource.getLastRequest(), PayPalResource.getLastResponse(), null);
			} catch (final PayPalRESTException e) {
				ResultPrinter.addResult(req, resp, "Executed The Payment", PayPalResource.getLastRequest(), null, e.getMessage());
			}
		} else {

			// ###Details
			// Let's you specify details of a payment amount.
			final Details details = new Details();
			details.setShipping("1");
			details.setSubtotal("5");
			details.setTax("1");

			// ###Amount
			// Let's you specify a payment amount.
			final Amount amount = new Amount();
			amount.setCurrency("EUR");
			// Total must be equal to sum of shipping, tax and subtotal.
			amount.setTotal("7");
			amount.setDetails(details);

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
			item.setName("Ground Coffee 40 oz").setQuantity("1").setCurrency("USD").setPrice("5");
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
			redirectUrls.setCancelUrl(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
			redirectUrls.setReturnUrl(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
			payment.setRedirectUrls(redirectUrls);

			// Create a payment by posting to the APIService
			// using a valid AccessToken
			// The return object contains the status;
			try {
				createdPayment = payment.create(apiContext);
				PaymentWithPayPalServlet.LOGGER.info("Created payment with id = " + createdPayment.getId() + " and status = " + createdPayment.getState());
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
