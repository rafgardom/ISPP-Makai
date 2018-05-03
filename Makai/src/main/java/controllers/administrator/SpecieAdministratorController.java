
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import services.NotificationService;
import services.SpecieService;
import controllers.AbstractController;
import domain.Banner;
import domain.Specie;

@Controller
@RequestMapping("/specie/admin")
public class SpecieAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SpecieService		specieService;

	@Autowired
	private BannerService		bannerService;

	@Autowired
	private NotificationService	notificationService;


	// Constructors -----------------------------------------------------------

	public SpecieAdministratorController() {
		super();
	}

	// Create -----------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Specie specie;

		specie = this.specieService.create();

		result = this.createEditModelAndView(specie);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final Specie specie, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(specie);
		} else
			try {
				this.specieService.save(specie);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(specie, "specie.commit.error");
			}
		return result;
	}

	// Listing  ------------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Specie> species;
		Integer numberNoti;
		try {
			species = this.specieService.findAll();
			numberNoti = this.notificationService.findNotificationWithoutRead();
			final Boolean canDelete[] = new Boolean[species.size()];
			int i = 0;
			for (final Specie s : species) {
				canDelete[i] = this.specieService.tieneBreedUnaSpecie(s);
				i++;
			}

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("specie/list");
			result.addObject("requestURI", "specie/admin/list.do");
			result.addObject("numberNoti", numberNoti);
			result.addObject("species", species);
			result.addObject("canDelete", canDelete);
			//			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			//			result.addObject("imagesRight", imagesRight);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}
	// Delete --------------------------------------------------------------------		

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int specieId) {
		ModelAndView result;
		Specie specie;
		try {
			specie = this.specieService.findOne(specieId);

			this.specieService.delete(specie);

			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			System.out.println(oops.toString());
			result = new ModelAndView("error");
		}
		return result;
	}

	// Edit -----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int specieId) {
		ModelAndView result;
		Specie specie;
		try {
			specie = this.specieService.findOne(specieId);

			result = this.createEditModelAndView(specie);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final Specie specie, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(specie);
		} else
			try {
				this.specieService.save(specie);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(specie, "specie.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Specie specie) {
		ModelAndView result;

		result = this.createEditModelAndView(specie, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Specie specie, final String message) {
		ModelAndView result;
		Integer numberNoti;
		result = new ModelAndView("specie/create");
		numberNoti = this.notificationService.findNotificationWithoutRead();
		if (specie.getId() == 0)
			result.addObject("RequestURI", "specie/admin/create.do");
		else
			result.addObject("RequestURI", "specie/admin/edit.do?specieId=" + specie.getId());
		result.addObject("errorMessage", message);
		result.addObject("numberNoti", numberNoti);
		result.addObject("specie", specie);

		return result;
	}

}
