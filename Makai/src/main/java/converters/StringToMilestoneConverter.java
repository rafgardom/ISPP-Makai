
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MilestoneRepository;
import domain.Milestone;

@Component
@Transactional
public class StringToMilestoneConverter implements Converter<String, Milestone> {

	@Autowired
	MilestoneRepository	milestoneRepository;


	@Override
	public Milestone convert(final String text) {
		Milestone result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.milestoneRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
