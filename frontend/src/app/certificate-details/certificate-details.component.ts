import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CertificateService } from '../services/certificate.service';

@Component({
  selector: 'app-certificate-details',
  templateUrl: './certificate-details.component.html',
  styleUrls: ['./certificate-details.component.css']
})
export class CertificateDetailsComponent implements OnInit {
  constructor(private route: ActivatedRoute, private certificateService: CertificateService) {}

  ngOnInit() {
    const serialNumber = +this.route.snapshot.params.id;
    this.certificateService.getCertificateBySerialNumber(serialNumber)
    .subscribe(
      resp => {
        console.log('Response');
      }
    );
  }
}
