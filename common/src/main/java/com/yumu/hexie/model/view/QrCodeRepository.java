package com.yumu.hexie.model.view;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeRepository extends JpaRepository<QrCode, Long> {

	QrCode findByFromSys(String fromSys);
}
