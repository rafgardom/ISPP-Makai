
package paypal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResultPrinter {

	public static void addResult(final HttpServletRequest req, final HttpServletResponse resp, final String message, final String request, String response, final String error) {

		ResultPrinter.addDataToAttributeList(req, "messages", message);
		ResultPrinter.addDataToAttributeList(req, "requests", request);
		response = (response != null) ? response : error;
		ResultPrinter.addDataToAttributeList(req, "responses", response);
		ResultPrinter.addDataToAttributeList(req, "errors", error);
		if (error != null)
			try {
				req.getRequestDispatcher("response.jsp").forward(req, resp);
			} catch (final ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@SuppressWarnings("unchecked")
	private static void addDataToAttributeList(final HttpServletRequest req, final String listName, final String data) {
		// Add Messages
		List<String> list;
		if ((list = (List<String>) req.getAttribute(listName)) == null)
			list = new ArrayList<String>();
		list.add(data);
		req.setAttribute(listName, list);
	}
}
