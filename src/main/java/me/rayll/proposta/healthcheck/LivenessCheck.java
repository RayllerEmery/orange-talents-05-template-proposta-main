package me.rayll.proposta.healthcheck;

import org.springframework.boot.actuate.availability.AvailabilityStateHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.LivenessState;
import org.springframework.stereotype.Component;

@Component
public class LivenessCheck extends AvailabilityStateHealthIndicator {

	public LivenessCheck(ApplicationAvailability availability) {
		super(availability, LivenessState.class, (statusMappings) -> {
			statusMappings.add(LivenessState.CORRECT, Status.UP);
			statusMappings.add(LivenessState.BROKEN, Status.DOWN);
		});
	}

	@Override
	protected AvailabilityState getState(ApplicationAvailability applicationAvailability) {
		return applicationAvailability.getLivenessState();
	}
}
