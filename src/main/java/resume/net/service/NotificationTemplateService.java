package resume.net.service;

import resume.net.model.NotificationMessage;

public interface NotificationTemplateService {

	NotificationMessage createNotificationMessage (String templateName, Object model);
}
