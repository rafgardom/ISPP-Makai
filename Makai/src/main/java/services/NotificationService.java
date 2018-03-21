
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NotificationRepository;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.Notification;
import domain.NotificationType;
import domain.Request;
import domain.Trainer;

@Service
@Transactional
public class NotificationService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private NotificationRepository	notificationRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private TrainerService			trainerService;


	// Constructors -----------------------------------------------------------
	public NotificationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Notification findOne(final int notificationId) {
		Notification result;

		result = this.notificationRepository.findOne(notificationId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Notification> findAll() {
		Collection<Notification> result;

		result = this.notificationRepository.findAll();

		return result;
	}

	public Notification create() {
		Notification result;
		Administrator principal;
		Calendar calendar;
		Collection<Actor> actors;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		actors = new HashSet<Actor>();

		result = new Notification();
		result.setActors(actors);
		result.setMoment(calendar.getTime());

		return result;
	}

	public Notification create(final Collection<Actor> actors) {
		Notification result;
		Actor principal;
		Calendar calendar;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result = new Notification();
		result.setActors(actors);
		result.setMoment(calendar.getTime());

		return result;
	}

	public Notification save(final Notification notification) {
		Assert.notNull(notification);
		Notification result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.notificationRepository.save(notification);

		return result;
	}

	public void delete(final Notification notification) {
		Actor principal;
		Collection<Actor> actors;

		Assert.notNull(notification);
		Assert.isTrue(notification.getId() != 0);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		actors = notification.getActors();
		Assert.isTrue(actors.contains(principal));	//Comprueba si está dentro de sus notificaciones

		if (actors.size() <= 1)	//Si es el único se borra la notificación
			this.notificationRepository.delete(notification);
		else {	//Si no elimina solo su relación
			actors.remove(principal);
			notification.setActors(actors);
			this.notificationRepository.save(notification);
		}

	}

	// Other business methods -------------------------------------------------

	public Collection<Notification> findByActorId(final int actorId) {
		Collection<Notification> result;

		result = this.notificationRepository.findByActorId(actorId);

		return result;
	}

	public void createNotificationForRequestWithOffer(final Request request) {
		Assert.notNull(request);
		Notification notification;
		Customer customer;
		final Collection<Actor> actors = new ArrayList<Actor>();

		customer = request.getCustomer();
		actors.add(customer);
		notification = this.create(actors);
		notification.setReason("Nueva oferta en su solicitud: " + request.getTags());
		notification.setDescription("A un entrenador le interesa su solicitud: " + request.getTags());
		notification.setType(NotificationType.REQUEST);

		this.save(notification);

	}

	public void createNotificationToTrainerWithTraining(final Request request) {
		Assert.notNull(request);
		Notification notification;
		Collection<Trainer> trainers;
		final Collection<Actor> actors = new ArrayList<Actor>();

		trainers = this.trainerService.findTrainerSameCategory(request.getCategory());
		for (final Trainer t : trainers)
			actors.add(t);
		notification = this.create(actors);
		notification.setReason("Nueva solicitud con su misma categoría: " + request.getCategory());
		notification.setDescription("Podría interesarle crear una oferta a dicha solicitud: " + request.getTags());
		notification.setType(NotificationType.REQUEST);

		this.save(notification);

	}
}
