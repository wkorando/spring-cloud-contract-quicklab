package com.ibm.developer.produce;

import org.springframework.stereotype.Service;

@Service
public class ProduceService {

	private ProduceRepo repo;

	public ProduceService(ProduceRepo repo) {
		this.repo = repo;
	}

	public Produce addNewProduce(Produce produce) {
		if (produce.getName().isEmpty() || !(produce.getQuantity() > 0)) {
			throw new ClientException("Missing required value!");
		}
		return repo.save(produce);
	}

	public Iterable<Produce> findProduceByName(String name) {
		if (name.matches("[\\W]+")) {
			throw new ClientException("Produce name must be alpha numeric!");
		}
		return repo.findByName(name);
	}
}
