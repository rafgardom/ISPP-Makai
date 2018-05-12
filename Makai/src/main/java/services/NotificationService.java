
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

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
import domain.Offer;
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

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result = new Notification();
		result.setActor(principal);
		result.setMoment(calendar.getTime());
		result.setIsRead(false);

		return result;
	}

	public Notification create(final Actor actor) {
		Notification result;
		Actor principal;
		Calendar calendar;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result = new Notification();
		result.setActor(actor);
		result.setMoment(calendar.getTime());
		result.setIsRead(false);

		return result;
	}

	public Notification save(final Notification notification) {
		Assert.notNull(notification);
		Notification result;
		Actor principal;

		if (notification.getType().getName() != "BANNER") {
			principal = this.actorService.findByPrincipal();
			Assert.notNull(principal);
		}

		result = this.notificationRepository.save(notification);

		return result;
	}

	public void saveForAll(final Notification notification) {
		Assert.notNull(notification);
		Actor principal;
		Collection<Actor> actors;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		if (notification.getReason().substring(0, 1).equals("#"))
			notification.setReason("#" + notification.getReason());

		actors = this.actorService.findAllNotAdmin();
		for (final Actor a : actors) {
			notification.setActor(a);
			this.notificationRepository.save(notification);
		}
	}

	public void delete(final Notification notification) {
		Actor principal;
		Actor actor;

		Assert.notNull(notification);
		Assert.isTrue(notification.getId() != 0);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		actor = notification.getActor();
		Assert.isTrue(actor.equals(principal)); //Comprueba si esta dentro de sus notificaciones
		this.notificationRepository.delete(notification.getId());

	}

	public void deleteAllMyNotifications() {
		Actor principal;
		Collection<Notification> notifications = new ArrayList<Notification>();

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		notifications = this.findByActorId(principal.getId());

		for (final Notification aux : notifications)
			this.notificationRepository.delete(aux);

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

		customer = request.getCustomer();
		notification = this.create(customer);
		notification.setReason("#OC0");
		notification.setDescription(": " + request.getTags());
		notification.setType(NotificationType.REQUEST);

		this.save(notification);

	}

	public void createNotificationOfferAcceptedTrainer(final Offer offer) {
		Assert.notNull(offer);
		Notification notification;
		Trainer trainer;

		trainer = offer.getTrainer();
		notification = this.create(trainer);
		notification.setReason("#OA0");
		notification.setDescription(".");
		notification.setType(NotificationType.OFFER);

		this.save(notification);

	}

	public void createNotificationToTrainerWithTraining(final Request request) {
		Assert.notNull(request);
		Notification notification;
		Collection<Trainer> trainers;

		trainers = this.trainerService.findTrainerSameCategory(request.getCategory());
		for (final Trainer t : trainers) {
			notification = this.create(t);
			notification.setReason("#RC0" + request.getCategory());
			notification.setDescription(": " + request.getTags());
			notification.setType(NotificationType.REQUEST);
			this.save(notification);
		}

	}

	public Notification createForBanner() {
		Notification result;
		Calendar calendar;
		Actor administrator;

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		administrator = this.actorService.findAdministrator();

		result = new Notification();
		result.setActor(this.actorService.findOne(administrator.getId()));
		result.setMoment(calendar.getTime());
		result.setIsRead(false);
		result.setType(NotificationType.BANNER);

		return result;
	}

	public void notificationViewed(final Notification notification) {
		Assert.notNull(notification);
		if (notification.getIsRead() == false)
			notification.setIsRead(true);

		this.save(notification);
	}

	public Integer findNotificationWithoutRead() {
		Actor actor;

		try {
			actor = this.actorService.findByPrincipal();
			return this.notificationRepository.findNotificationWithoutRead(actor.getId());
		} catch (final Throwable e) {
			return 0;
		}

	}
}
