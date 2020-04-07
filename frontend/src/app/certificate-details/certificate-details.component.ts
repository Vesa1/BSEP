import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CertificateService } from '../services/certificate.service';
import { CertificateDetails } from '../model/certificate-details.model';

@Component({
  selector: 'app-certificate-details',
  templateUrl: './certificate-details.component.html',
  styleUrls: ['./certificate-details.component.css']
})
export class CertificateDetailsComponent implements OnInit {
  certificate: CertificateDetails;

  constructor(
    private route: ActivatedRoute,
    private certificateService: CertificateService
  ) {}

  ngOnInit() {
    const serialNumber = +this.route.snapshot.params.id;
    this.certificateService
      .getCertificateBySerialNumber(serialNumber)
      .subscribe(resp => {
        this.certificate = resp;
      });
  }

  revokeCertificate(serialNumber: number) {
    this.certificateService
      .revokeCertificate(serialNumber)
      .subscribe(resp => console.log('SUCCESS'));
  }

  printCertificate(serialNumber: number) {
    this.certificateService
      .printCertificate(serialNumber)
      .subscribe(resp => console.log('SUCCESS'));
  }
}
