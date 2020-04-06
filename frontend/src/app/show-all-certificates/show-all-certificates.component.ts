import { Component, OnInit } from '@angular/core';

import { MatTableModule } from '@angular/material/table';
import { MatTableDataSource } from '@angular/material';
import { CertificateService } from '../services/certificate.service';

export interface Certificate {
  serialNumber: string;
  issuerOrganization: number;
  issuerOrganizationalUnit: number;
  subjectOrganization: string;
  subjectOrganizationalUnit: string;
}

@Component({
  selector: 'app-show-all-certificates',
  templateUrl: './show-all-certificates.component.html',
  styleUrls: ['./show-all-certificates.component.css']
})
export class ShowAllCertificatesComponent implements OnInit {
  displayedColumns: string[] = [
    'serialNumber',
    'issuerOrganization',
    'issuerOrganizationalUnit',
    'subjectOrganization',
    'subjectOrganizationalUnit',
    'action'
  ];
  dataSource: MatTableDataSource<Certificate>;
  certificates: Certificate[];

  constructor(private certificateService: CertificateService) {}

  ngOnInit() {
    this.certificateService.getAllCertificates().subscribe(resp => {
      this.certificates = resp;
      this.dataSource = new MatTableDataSource(this.certificates);
    });
  }

  certificateDetails(serialNumber: number) {
    console.log('Details', serialNumber);
  }
}
