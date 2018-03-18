
package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import forms.CustomerForm;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CustomerRepository	customerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	// Constructors------------------------------------------------------------
	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Customer findOne(final int customerId) {
		Customer result;

		result = this.customerRepository.findOne(customerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> result;

		result = this.customerRepository.findAll();

		return result;
	}

	public Customer create() {
		final UserAccount userAccount = new UserAccount();

		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority(Authority.CUSTOMER);
		authorities.add(a);
		userAccount.setAuthorities(authorities);

		final Customer result = new Customer();

		result.setUserAccount(userAccount);
		result.setAvgRating(0.0);

		return result;
	}

	public CustomerForm createForm() {
		CustomerForm result;
		result = new CustomerForm();

		return result;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		Customer result;

		result = this.customerRepository.save(customer);
		return result;
	}

	// Other business methods -------------------------------------------------

	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Customer findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Customer result;

		result = this.customerRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Customer reconstruct(final CustomerForm customerForm, final BindingResult binding) throws IOException {
		Assert.notNull(customerForm);
		Customer result;
		String password;

		if (customerForm.getId() == 0) {
			result = this.create();

			if (customerForm.getPassword().equals(customerForm.getRepeatPassword()) && customerForm.getPassword() != null && !customerForm.getPassword().isEmpty()) {
				password = this.actorService.hashPassword(customerForm.getPassword());
				result.getUserAccount().setPassword(password);
				result.getUserAccount().setUsername(customerForm.getUserName());
			} else {
				FieldError fieldError;
				final String[] codes = {
					"customer.password.error"
				};
				fieldError = new FieldError("customerForm", "password", result.getUserAccount().getPassword(), false, codes, null, "");
				binding.addError(fieldError);

			}
			result.getUserAccount().setEnabled(true);

		} else
			result = this.findOne(customerForm.getId());

		result.setCoordinates(customerForm.getCoordinates());
		result.setEmail(customerForm.getEmail());
		result.setName(customerForm.getName());
		result.setNid(customerForm.getNid());
		result.setPhone(customerForm.getPhone());
		result.setPicture(customerForm.getPicture());
		result.setSurname(customerForm.getSurname());

		final MultipartFile userImage = customerForm.getUserImage();
		result.setPicture(userImage.getBytes());

		if (result.getPicture().length == 0) {
			FieldError fieldError;
			final String[] codes = {
				"customer.register.picture.empty.error"
			};
			fieldError = new FieldError("customerForm", "userImage", customerForm.getUserImage(), false, codes, null, "");
			binding.addError(fieldError);
		}

		this.validator.validate(result, binding);

		return result;

	}

	public CustomerForm toFormObject(final Customer customer) {
		Assert.notNull(customer);
		final CustomerForm result = new CustomerForm();

		result.setCoordinates(customer.getCoordinates());
		result.setEmail(customer.getEmail());
		result.setId(customer.getId());
		result.setName(customer.getName());
		result.setNid(customer.getNid());
		result.setPhone(customer.getPhone());
		result.setPicture(customer.getPicture());
		result.setSurname(customer.getSurname());

		return result;
	}
}
