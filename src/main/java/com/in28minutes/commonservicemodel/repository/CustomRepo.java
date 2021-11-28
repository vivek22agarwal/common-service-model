package com.in28minutes.commonservicemodel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;

public interface CustomRepo<T,ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, RevisionRepository<T, ID, Integer> {

	Optional<T> findByIdAndIsActive(ID id, Boolean isActive);
}
