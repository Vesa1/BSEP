import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CertificateService } from '../services/certificate.service';

export interface Certificate {
  serialNumber: string;
  issuerOrganization: number;
  issuerOrganizationalUnit: number;
  subjectOrganization: string;
  subjectOrganizationalUnit: string;
}

@Component({
  selector: 'app-issuer-list',
  templateUrl: './issuer-list.component.html',
  styleUrls: ['./issuer-list.component.css']
})
export class IssuerListComponent implements OnInit {

  public certificates: Certificate[];
  @Output() 
  select: EventEmitter<any> = new EventEmitter();
  constructor(private certificateService: CertificateService) { }

  ngOnInit() {
    this.certificateService.issuerList().subscribe(resp => {
      this.certificates = resp;
      console.log(this.certificates);
    });
  }

  onSelect(val) {
    console.log(val);
    this.select.emit(val);
  }
}