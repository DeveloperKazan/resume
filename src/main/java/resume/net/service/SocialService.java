package resume.net.service;

import resume.net.entity.Profile;

public interface SocialService<T> {

	Profile loginViaSocialNetwork(T model);
}
