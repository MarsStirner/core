package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TempInvalid_Period database table.
 * 
 */
@Entity
@Table(name="TempInvalid_Period")
public class TempInvalidPeriod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date begDate;

    @ManyToOne
    @JoinColumn(name = "begPerson_id")
	private Staff begPerson;

	@Column(name="break_id")
	private Integer breakId;

    @ManyToOne
    @JoinColumn(name = "diagnosis_id")
	private Diagnosis diagnosis;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date endDate;

    @ManyToOne
    @JoinColumn(name = "endPerson_id")
	private Staff endPerson;

	@Column(name = "isExternal", nullable=false)
	private boolean isExternal;

    @ManyToOne
    @JoinColumn(name = "master_id", nullable=false)
	private TempInvalid master;

	@Column(nullable=false, length=256)
	private String note;

	@Column(name="regime_id")
	private int regimeId;

	@Column(name="result_id")
	private int resultId;

	public TempInvalidPeriod() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBegDate() {
		return this.begDate;
	}

	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}

	public Staff getBegPerson_id() {
		return this.begPerson;
	}

	public void setBegPerson_id(Staff begPerson_id) {
		this.begPerson = begPerson;
	}

	public int getBreakId() {
		return this.breakId;
	}

	public void setBreakId(int breakId) {
		this.breakId = breakId;
	}

	public Diagnosis getDiagnosisId() {
		return this.diagnosis;
	}

	public void setDiagnosisId(Diagnosis diagnosisId) {
		this.diagnosis = diagnosis;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Staff getEndPerson_id() {
		return this.endPerson;
	}

	public void setEndPerson_id(Staff endPerson) {
		this.endPerson = endPerson;
	}

	public boolean getIsExternal() {
		return this.isExternal;
	}

	public void setIsExternal(boolean isExternal) {
		this.isExternal = isExternal;
	}

	public TempInvalid getMaster() {
		return this.master;
	}

	public void setMaster(TempInvalid master) {
		this.master = master;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getRegimeId() {
		return this.regimeId;
	}

	public void setRegimeId(int regimeId) {
		this.regimeId = regimeId;
	}

	public int getResultId() {
		return this.resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

}