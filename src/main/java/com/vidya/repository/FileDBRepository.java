package com.vidya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidya.model.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String>{

}
