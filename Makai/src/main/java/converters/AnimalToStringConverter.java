package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Animal;

@Component
@Transactional
public class AnimalToStringConverter implements Converter<Animal, String> {

	@Override
	public String convert(Animal animal) {
		String result;

		if (animal == null){
			result = null;
		}else{
			result = String.valueOf(animal.getId());
		}
		return result;
	}

}
