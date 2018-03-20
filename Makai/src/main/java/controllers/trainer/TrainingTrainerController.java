
package controllers.trainer;

import java.io.IOException;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TrainerService;
import services.TrainingService;
import controllers.AbstractController;
import domain.Trainer;
import domain.Training;

@Controller
@RequestMapping("/training/trainer")
public class TrainingTrainerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TrainingService	trainingService;

	@Autowired
	private TrainerService	trainerService;


	// Constructors -----------------------------------------------------------

	public TrainingTrainerController() {
		super();
	}

	// List -------------------------------------------------------------------	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Trainer trainer;
		Collection<Training> trainings;

		trainer = this.trainerService.findByPrincipal();
		trainings = this.trainingService.findByTrainerId(trainer.getId());

		result = new ModelAndView("training/list");
		result.addObject("trainings", trainings);
		result.addObject("requestURI", "training/trainer/list.do");

		return result;
	}

	// Display ----------------------------------------------------------------	

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int trainingId) {
		ModelAndView result;
		Training training;

		training = this.trainingService.findOne(trainingId);

		result = new ModelAndView("training/display");
		result.addObject("training", training);
		result.addObject("requestURI", "training/trainer/display.do");

		return result;
	}

	// Delete -----------------------------------------------------------------	

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int trainingId) {
		ModelAndView result;
		Training training;

		training = this.trainingService.findOne(trainingId);
		this.trainingService.delete(training);

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	// Create -----------------------------------------------------------------	

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Training training;

		training = this.trainingService.create();

		result = new ModelAndView("training/create");
		result.addObject("training", training);
		result.addObject("requestURI", "training/trainer/edit.do");

		return result;
	}

	// Edit -------------------------------------------------------------------	

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int trainingId) {
		ModelAndView result;
		Training training;

		training = this.trainingService.findOne(trainingId);

		result = new ModelAndView("training/edit");
		result.addObject("training", training);
		result.addObject("requestURI", "training/trainer/edit.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Training training, final BindingResult binding) throws IOException {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createModelAndView(training);
		} else
			try {

				this.trainingService.save(training);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				System.out.println(oops.toString());
				result = this.createModelAndView(training, "training.commit.error");

			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------	

	protected ModelAndView createModelAndView(final Training training) {
		final ModelAndView result = this.createModelAndView(training, null);
		return result;
	}

	protected ModelAndView createModelAndView(final Training training, final String message) {

		ModelAndView result;

		if (training.getId() == 0) {
			result = new ModelAndView("training/create");
			result.addObject("requestURI", "training/trainer/create.do");
		} else {
			result = new ModelAndView("training/edit");
			result.addObject("requestURI", "training/trainer/edit.do");
		}

		result.addObject("training", training);
		result.addObject("message", message);

		return result;
	}
}
