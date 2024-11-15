package com.exmcs.log_service.model.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@Table(name = "log_transaksi")
public class LogTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "csv_filename")
    private String csvFilename;

    @Column(name = "total_record")
    private Integer totalRecord;

    @Column(name = "total_record_failed")
    private Integer totalRecordFailed;

    @Column(name = "total_record_success")
    private Integer totalRecordSuccess;

    @Column(name = "failed_id_notes")
    private String failedIdNotes;

    @Column(name = "upload_date")
    @Temporal(TemporalType.DATE)
    private Date uploadDate;
}