package com.ibm.developer.produce;

import org.springframework.data.repository.CrudRepository;

public interface ProduceRepo extends CrudRepository<Produce, String> {
	 Iterable<Produce> findByName(String name);
}
