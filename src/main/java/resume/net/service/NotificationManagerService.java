package resume.net.service;

import resume.net.entity.Profile;


public interface NotificationManagerService {

	void sendRestoreAccessLink(Profile profile, String restoreLink);

	void sendPasswordChanged(Profile profile);
}
