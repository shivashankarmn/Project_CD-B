package com.swms.swms.repository;

import com.swms.swms.model.WasteCollectionRecord;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteCollectionRecordRepository extends JpaRepository<WasteCollectionRecord, Integer> {
    Optional<WasteCollectionRecord> findByRequest_Id(int requestId);
}
