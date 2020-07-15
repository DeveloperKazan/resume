package resume.net.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfb.types.User;

import resume.net.entity.Profile;
import resume.net.repository.storage.ProfileRepository;
import resume.net.service.SocialService;

@Service
public class FacebookSocialService implements SocialService<User> {

	@Autowired
	private ProfileRepository profileRepository;
	
	@Override
	public Profile loginViaSocialNetwork(User model) {
		if(StringUtils.isNotBlank(model.getEmail())) {
			Profile p = profileRepository.findByEmail(model.getEmail());
			if(p != null){
				return p;
			}
		}

		return null;
	}

}
