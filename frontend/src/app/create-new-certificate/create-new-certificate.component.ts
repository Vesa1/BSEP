import { Component, OnInit, Input } from '@angular/core';
import { NewCertificate } from '../model/new-certificate';
import { NgForm } from '@angular/forms';
import { CertificateService } from '../services/certificate.service';
@Component({
  selector: 'app-create-new-certificate',
  templateUrl: './create-new-certificate.component.html',
  styleUrls: ['./create-new-certificate.component.css']
})
export class CreateNewCertificateComponent implements OnInit {
  isClicked = false;
  newCertificate: NewCertificate;
  private selfSigned;
  @Input() select;

  constructor(
    private certificateService: CertificateService
  ) {}

  ngOnInit() {}

  onSumbitCreateNew(newCertificateForm: NgForm) {
    this.isClicked = true;
    if (newCertificateForm.form.valid) {
      this.newCertificate = newCertificateForm.value;
      this.newCertificate.serialNumber = this.select;
      this.certificateService.createNewCertificate(
        this.newCertificate
      );
    }
  }

  onChange(value:string) {
    this.select = value;
    console.log(value);
  }
}
