package resume.net.repository.storage;

import org.springframework.data.repository.CrudRepository;

import resume.net.entity.ProfileRestore;


public interface ProfileRestoreRepository extends CrudRepository<ProfileRestore, Long> {
	
	ProfileRestore findByToken(String token);
}
