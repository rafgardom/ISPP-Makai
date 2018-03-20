
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TrainingRepository;
import domain.Trainer;
import domain.Training;

@Service
@Transactional
public class TrainingService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TrainingRepository	trainingRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private TrainerService		trainerService;


	// Constructors------------------------------------------------------------
	public TrainingService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Training findOne(final int trainingId) {
		Training result;

		result = this.trainingRepository.findOne(trainingId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Training> findAll() {
		Collection<Training> result;

		result = this.trainingRepository.findAll();

		return result;
	}

	public Training create() {
		Training result;
		Trainer principal;

		principal = this.trainerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Training();
		result.setTrainer(principal);

		return result;
	}

	public Training save(final Training training) {
		Assert.notNull(training);
		Training result;
		Trainer principal;

		principal = this.trainerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(training.getTrainer().getId() == principal.getId());

		if (training.getDuration().getDay() == null)
			training.getDuration().setDay(0);
		if (training.getDuration().getMonth() == null)
			training.getDuration().setMonth(0);
		if (training.getDuration().getWeek() == null)
			training.getDuration().setWeek(0);
		if (training.getDuration().getYear() == null)
			training.getDuration().setYear(0);

		Assert.isTrue(!(training.getDuration().getDay() == 0 && training.getDuration().getMonth() == 0 && training.getDuration().getWeek() == 0 && training.getDuration().getYear() == 0));

		result = this.trainingRepository.save(training);

		return result;
	}

	public void delete(final Training training) {
		Trainer principal;

		Assert.notNull(training);
		Assert.isTrue(training.getId() != 0);

		principal = this.trainerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(training.getTrainer().getId() == principal.getId());

		this.trainingRepository.delete(training);
	}

	// Other business methods -------------------------------------------------

	public Collection<Training> findByTrainerId(final int trainerId) {
		Collection<Training> result;

		result = this.trainingRepository.findByTrainerId(trainerId);

		return result;
	}
}
