package telran.monitoring.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.entities.*;
import telran.monitoring.repo.DoctorRepo;
import telran.monitoring.repo.PatientRepo;
import telran.monitoring.repo.VisitRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailDataProviderServiceImpl implements EmailDataProviderService{
	final DoctorRepo doctorRepo;
	final PatientRepo patientRepo;
	final VisitRepo visitRepo;
	public EmailNotificationData getEmailData(long patientId) {
		Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new RuntimeException("patient not found"));
		DoctorId doctorId = visitRepo.findDoctorIdLastVisit(patientId);
		if(doctorId == null || doctorId.getId() == null) {
			throw new RuntimeException("doctor not found");
		}
		log.trace("doctorId is {}", doctorId.getId());
		Doctor doctor = doctorRepo.findById(doctorId.getId()).orElseThrow(() -> new IllegalStateException("doctor not found"));
		return new EmailNotificationData(doctor.getEmail(), doctor.getName(), patient.getName());
	}
}
