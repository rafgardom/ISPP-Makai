
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Milestone;

@Component
@Transactional
public class MilestoneToStringConverter implements Converter<Milestone, String> {

	@Override
	public String convert(final Milestone milestone) {
		String result;

		if (milestone == null)
			result = null;
		else
			result = String.valueOf(milestone.getId());
		return result;
	}

}
