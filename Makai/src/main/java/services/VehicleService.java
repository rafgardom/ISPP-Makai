
package services;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import repositories.VehicleRepository;
import utilities.Utilities;
import domain.Transporter;
import domain.Travel;
import domain.Vehicle;
import forms.VehicleForm;

@Service
@Transactional
public class VehicleService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private VehicleRepository	vehicleRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private TransporterService	transporterService;

	@Autowired
	private TravelService		travelService;

	@Autowired
	private Validator			validator;


	// Constructors------------------------------------------------------------
	public VehicleService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Vehicle findOne(final int vehicleId) {
		Vehicle result;

		result = this.vehicleRepository.findOne(vehicleId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Vehicle> findAll() {
		Collection<Vehicle> result;

		result = this.vehicleRepository.findAll();

		return result;
	}

	public VehicleForm createForm() {
		final VehicleForm result = new VehicleForm();

		return result;
	}

	public Vehicle create() {
		Vehicle result;
		Transporter principal;

		principal = this.transporterService.findByPrincipal();
		Assert.notNull(principal);

		result = new Vehicle();
		result.setTransporter(principal);
		result.setIsActived(true);

		return result;
	}

	public Vehicle save(final Vehicle vehicle) {
		Assert.notNull(vehicle);
		Vehicle result;
		Transporter principal;
		Calendar today;

		principal = this.transporterService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(vehicle.getTransporter().getId() == principal.getId());

		// Hacer comprobacion del a�o
		today = Calendar.getInstance();
		Assert.isTrue(today.get(Calendar.YEAR) >= vehicle.getYear());

		result = this.vehicleRepository.save(vehicle);

		return result;
	}

	public void delete(final Vehicle vehicle) {
		Assert.notNull(vehicle);
		Transporter principal;
		Collection<Travel> travels;
		Boolean hasTravelActive = false;

		principal = this.transporterService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(vehicle.getTransporter().getId() == principal.getId());
		Assert.isTrue(vehicle.getIsActived());

		travels = this.travelService.findTravelByVehicleId(vehicle);

		if (travels.isEmpty())
			this.vehicleRepository.delete(vehicle);
		else {
			hasTravelActive = this.checkTravelActive(travels);
			Assert.isTrue(!hasTravelActive);	// comprueba de que no tenga ningún viaje sin comenzar
			vehicle.setIsActived(false);
			this.vehicleRepository.save(vehicle);
		}

	}
	public Vehicle reconstruct(final VehicleForm vehicleForm, final BindingResult binding) throws IOException {
		Assert.notNull(vehicleForm);
		Vehicle result;

		if (vehicleForm.getUserImage().getSize() != 0 && (!vehicleForm.getUserImage().getContentType().contains("image")) || vehicleForm.getUserImage().getSize() > 2097152) {
			FieldError fieldError;
			final String[] codes = {
				"vehicle.picture.register.error"
			};
			fieldError = new FieldError("vehicleForm", "userImage", vehicleForm.getUserImage(), false, codes, null, "");
			binding.addError(fieldError);
		}

		if (vehicleForm.getId() == 0)
			result = this.create();
		else
			result = this.findOne(vehicleForm.getId());

		result.setBrand(vehicleForm.getBrand());
		result.setSeats(vehicleForm.getSeats());
		result.setCarType(vehicleForm.getCarType());
		result.setAccommodation(vehicleForm.getAccommodation());
		result.setYear(vehicleForm.getYear());
		result.setDescription(vehicleForm.getDescription());
		result.setColor(vehicleForm.getColor());

		final Pattern newLicensePatter = Pattern.compile("^\\d{4}[A-Z]{3}");
		final Pattern oldLicensePattern = Pattern.compile("^([A-Z]{1})?([A-Z]{2})?\\d{4}[A-Z]{2}");
		newLicensePatter.matcher(vehicleForm.getLicense());
		if (!newLicensePatter.matcher(vehicleForm.getLicense()).matches() && !oldLicensePattern.matcher(vehicleForm.getLicense()).matches()) {
			FieldError fieldError;
			final String[] codes = {
				"vehicle.license.error"
			};
			fieldError = new FieldError("vehicleForm", "license", vehicleForm.getLicense(), false, codes, null, "");
			binding.addError(fieldError);
		}
		result.setLicense(vehicleForm.getLicense());

		if (vehicleForm.getUserImage().getSize() > 0)
			result.setPicture(vehicleForm.getUserImage().getBytes());

		this.validator.validate(result, binding);

		return result;
	}
	public VehicleForm toFormObject(final Vehicle vehicle) {
		Assert.notNull(vehicle);
		final String image;
		final VehicleForm result = new VehicleForm();
		final byte[] picture;
		picture = vehicle.getPicture();

		image = Utilities.showImage(picture, "vehicle");

		result.setAccommodation(vehicle.getAccommodation());
		result.setBrand(vehicle.getBrand());
		result.setCarType(vehicle.getCarType());
		result.setColor(vehicle.getColor());
		result.setDescription(vehicle.getDescription());
		result.setId(vehicle.getId());
		result.setLicense(vehicle.getLicense());
		result.setPicture(vehicle.getPicture());
		result.setSeats(vehicle.getSeats());
		result.setYear(vehicle.getYear());
		result.setStringImage(image);
		result.setId(vehicle.getId());

		return result;
	}
	public Collection<Vehicle> findVehicleByTransporterId(final int transporterId) {
		return this.vehicleRepository.findVehicleByTransporterId(transporterId);
	}

	public Collection<Vehicle> findActivatedVehicles(final Transporter transporter) {
		return this.vehicleRepository.findActivatedVehicles(transporter.getId());
	}

	public Boolean checkTravelActive(final Collection<Travel> travels) {
		Boolean res = false;

		for (final Travel travel : travels)
			if (travel.getStartMoment().after(new Date())) {
				res = true;
				break;
			}

		return res;
	}

}
