
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {

	@Query("select a from Animal a where a.animalShelter.id = ?1 or a.customer.id=?1")
	Collection<Animal> findByActorId(int actorId);

	@Query("select a from Animal a where a.animalShelter != null")
	Collection<Animal> findAnimalFromAnimalShelter();

	@Query("select a from Animal a where a.isHidden=false and (a.animalShelter.id = ?1 or a.customer.id=?1)")
	Collection<Animal> findByActorIdNotHidden(int actorId);

	@Query("select count(t.animals) from Travel t where t.id=?1")
	Integer countAnimalsByTravel(int travelId);

	@Query("select a from Animal a where a.isHidden=false and a.customer=null")
	Collection<Animal> findAnimalWithoutDeletedAndCustomer();

	@Query("select a from Animal a where a.chipNumber=?1")
	Animal findAnimalByChipNumber(String chipNumber);

	@Query("select a from Animal a where a.customer!=null and a.animalShelter.id = ?1")
	Collection<Animal> findByActorIdAndAdopted(int actorId);

	@Query("select a from Animal a where a.customer=null and a.animalShelter.id = ?1")
	Collection<Animal> findByActorIdAndNotAdopted(int actorId);

	@Query("select a from Animal a where a.chipNumber=?1")
	Animal findByChipNumber(String chipNumber);
}
