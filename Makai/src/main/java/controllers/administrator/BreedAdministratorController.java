
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
import services.BreedService;
import services.SpecieService;
import controllers.AbstractController;
import domain.Breed;
import domain.Specie;

@Controller
@RequestMapping("/breed/admin")
public class BreedAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BreedService	breedService;

	@Autowired
	private SpecieService	specieService;

	@Autowired
	private BannerService	bannerService;


	// Constructors -----------------------------------------------------------

	public BreedAdministratorController() {
		super();
	}

	// Create -----------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Breed breed;

		breed = this.breedService.create();

		result = this.createEditModelAndView(breed);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final Breed breed, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(breed);
		} else
			try {
				this.breedService.save(breed);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(breed, "breed.commit.error");
			}
		return result;
	}

	// Listing  ------------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Breed> breeds;

		try {
			breeds = this.breedService.findAll();

			final Boolean canDelete[] = new Boolean[breeds.size()];
			int i = 0;
			for (final Breed b : breeds) {
				canDelete[i] = this.breedService.tieneBreedUnAnimal(b);
				i++;
			}

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<String> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("breed/list");
			result.addObject("requestURI", "breed/admin/list.do");
			result.addObject("breeds", breeds);
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
	public ModelAndView ban(@RequestParam final int breedId) {
		ModelAndView result;
		Breed breed;
		try {
			breed = this.breedService.findOne(breedId);

			this.breedService.delete(breed);

			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			System.out.println(oops.toString());
			result = new ModelAndView("error");
		}
		return result;
	}

	// Edit -----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int breedId) {
		ModelAndView result;
		Breed breed;
		try {
			breed = this.breedService.findOne(breedId);

			result = this.createEditModelAndView(breed);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final Breed breed, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(breed);
		} else
			try {
				this.breedService.save(breed);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(breed, "breed.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Breed breed) {
		ModelAndView result;

		result = this.createEditModelAndView(breed, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Breed breed, final String message) {
		ModelAndView result;
		Collection<Specie> species;

		species = this.specieService.findAll();

		result = new ModelAndView("breed/create");
		if (breed.getId() == 0)
			result.addObject("RequestURI", "breed/admin/create.do");
		else
			result.addObject("RequestURI", "breed/admin/edit.do?breedId=" + breed.getId());
		result.addObject("breed", breed);
		result.addObject("species", species);
		result.addObject("errorMessage", message);

		return result;
	}

}
