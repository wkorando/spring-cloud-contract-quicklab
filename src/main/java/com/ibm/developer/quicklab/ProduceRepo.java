package com.ibm.developer.quicklab;

import org.springframework.data.repository.CrudRepository;

public interface ProduceRepo extends CrudRepository<Produce, String> {
	 Iterable<Produce> findByName(String name);
}
