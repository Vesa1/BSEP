import { Component, OnInit } from '@angular/core';
import { CertificateService } from '../services/certificate.service';

@Component({
  selector: 'app-issuer-list',
  templateUrl: './issuer-list.component.html',
  styleUrls: ['./issuer-list.component.css']
})
export class IssuerListComponent implements OnInit {

  constructor(private certificateService: CertificateService) { }

  ngOnInit() {
    this.certificateService.issuerList();
  }

}
