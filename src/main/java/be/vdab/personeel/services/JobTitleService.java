package be.vdab.personeel.services;

import java.util.List;
import java.util.Optional;

import be.vdab.personeel.entities.JobTitle;

public interface JobTitleService {

	public Optional<JobTitle> read(final long id);
	public List<JobTitle> findAll();
}
