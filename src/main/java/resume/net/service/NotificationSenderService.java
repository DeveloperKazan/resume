package resume.net.service;

import resume.net.entity.Profile;
import resume.net.model.NotificationMessage;

public interface NotificationSenderService {

	void sendNotification(NotificationMessage message);

	String getDestinationAddress(Profile profile);
}
