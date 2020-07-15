package resume.net.service;

import java.util.List;

import resume.net.entity.Profile;
import resume.net.entity.Skill;
import resume.net.entity.SkillCategory;
import resume.net.form.SignUpForm;


public interface EditProfileService {

	Profile createNewProfile(SignUpForm signUpForm);
	
	List<Skill> listSkills(long idProfile);

	List<SkillCategory> listSkillCategories();
	
	void updateSkills(long idProfile, List<Skill> skills);
}
