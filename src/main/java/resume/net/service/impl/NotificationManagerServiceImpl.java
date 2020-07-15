package resume.net.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import resume.net.entity.Profile;
import resume.net.model.NotificationMessage;
import resume.net.service.NotificationManagerService;
import resume.net.service.NotificationSenderService;
import resume.net.service.NotificationTemplateService;

@Service
public class NotificationManagerServiceImpl implements NotificationManagerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationManagerServiceImpl.class);

	@Autowired
	private NotificationSenderService notificationSenderService;

	@Autowired
	private NotificationTemplateService notificationTemplateService;

	@Override
	public void sendRestoreAccessLink(Profile profile, String restoreLink) {
		LOGGER.debug("Restore link: {} for account {}", restoreLink, profile.getUid());
		Map<String, Object> model = new HashMap<>();
		model.put("profile", profile);
		model.put("restoreLink", restoreLink);
		processNotification(profile, "restoreAccessNotification", model);
	}

	@Override
	public void sendPasswordChanged(Profile profile) {
		LOGGER.debug("Password changed for account {}", profile.getUid());
		Map<String, Object> model = new HashMap<>();
		model.put("profile", profile);
		processNotification(profile, "passwordChangedNotification", model);
	}

	private void processNotification(Profile profile, String templateName, Object model) {
		String destinationAddress = notificationSenderService.getDestinationAddress(profile);
		if (StringUtils.isNotBlank(destinationAddress)) {
			NotificationMessage notificationMessage = notificationTemplateService.createNotificationMessage(templateName, model);
			notificationMessage.setDestinationAddress(destinationAddress);
			notificationMessage.setDestinationName(profile.getFullName());
			notificationSenderService.sendNotification(notificationMessage);
		} else {
			LOGGER.error("Notification ignored: destinationAddress is empty for profile " + profile.getUid());
		}
	}
}
