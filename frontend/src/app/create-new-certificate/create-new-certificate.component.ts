import { Component, OnInit } from '@angular/core';
import { NewCertificate } from '../model/new-certificate';
import { CreateNewCertificateService } from '../services/create-new-certificate.service';
import { NgForm } from '@angular/forms';
@Component({
  selector: 'app-create-new-certificate',
  templateUrl: './create-new-certificate.component.html',
  styleUrls: ['./create-new-certificate.component.css']
})
export class CreateNewCertificateComponent implements OnInit {
  isClicked = false;
  newCertificate: NewCertificate;
  private selfSigned;

  constructor(
    private createNewCertificateService: CreateNewCertificateService
  ) {}

  ngOnInit() {}

  onSumbitCreateNew(newCertificateForm: NgForm) {
    this.isClicked = true;
    if (newCertificateForm.form.valid) {
      this.newCertificate = newCertificateForm.value;
      this.createNewCertificateService.createNewCertificate(
        this.newCertificate
      );
    }
  }
}
