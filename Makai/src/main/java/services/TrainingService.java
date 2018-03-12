
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
}
