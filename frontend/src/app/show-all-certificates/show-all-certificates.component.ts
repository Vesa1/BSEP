import { Component, OnInit } from '@angular/core';

import { MatTableDataSource } from '@angular/material';
import { MatRadioModule } from '@angular/material/radio';
import { CertificateService } from '../services/certificate.service';
import { Filter } from '../model/filter.model';

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
    'commonName',
    'action'
  ];
  dataSource: MatTableDataSource<Certificate>;
  certificates: Certificate[];
  showChain = false;
  chosenCertificate: Certificate;
  fetchedChain: string[];
  chosenType: string;
  chosenCN: string;
  types: string[] = ['Valid', 'Invalid'];
  filter: Filter;

  constructor(private certificateService: CertificateService) {}

  ngOnInit() {
    this.certificateService.getAllCertificates().subscribe(resp => {
      this.certificates = resp;
      this.dataSource = new MatTableDataSource(this.certificates);
    });
  }

  checkValidity(serialNumber: number) {
    this.certificateService.checkValidity(serialNumber).subscribe(resp => {
      if (resp === true)
        alert(`Certificate with serial number ${serialNumber} is valid `);
      else
        alert('Certificate with serial number ' + serialNumber + ' is invalid');
    });
  }

  checkRevocation(serialNumber: number) {
    this.certificateService.checkRevocation(serialNumber).subscribe(resp => {
      if(resp === true)
        alert('Certificate ' + serialNumber + ' - revoked');
      else 
        alert('Certificate ' + serialNumber + ' - not revoked');
    })
  }

  getCertificateChain(serialNumber: number) {
    this.certificates.map(cert => {
      if (+cert.serialNumber === +serialNumber) {
        this.chosenCertificate = cert;
      }
    });
    this.showChain = true;
    this.certificateService
      .getCertificateChain(serialNumber)
      .subscribe(resp => (this.fetchedChain = resp.reverse()));
  }

  search() {
    if (this.chosenCN === undefined && this.chosenCN === undefined) {
      this.certificateService.getAllCertificates().subscribe(resp => {
        this.certificates = resp;
        this.dataSource = new MatTableDataSource(this.certificates);
      });
    }

    this.filter = new Filter(this.chosenType, this.chosenCN);

    this.certificateService.getByFilter(this.filter).subscribe(resp => {
      this.certificates = resp;
      this.dataSource = new MatTableDataSource(this.certificates);
    });
  }
}
