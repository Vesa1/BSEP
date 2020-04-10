import { Component, OnInit, Input, ɵConsole } from '@angular/core';
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
  selfSigned : boolean;
  radioButtonValue: string;
  @Input() select;
  constructor(
    private certificateService: CertificateService
  ) {}

  ngOnInit() {
    this.selfSigned = true;
    this.radioButtonValue = "root";
  }

  onSumbitCreateNew(newCertificateForm: NgForm) {
    this.isClicked = true;
    if (newCertificateForm.form.valid) {
      this.newCertificate = newCertificateForm.value;
      this.newCertificate.serialNumber = this.select;
      this.newCertificate.selfSigned = this.selfSigned;
      this.newCertificate.certificateType = this.radioButtonValue;
      this.certificateService.createNewCertificate(
        this.newCertificate
      );
    }
  }

  onChange(value:string) {
    this.select = value;
    console.log("Select");
    console.log(value);
  }

  onChangeRadio(val) {
    console.log(this.radioButtonValue);
    if(this.radioButtonValue === "root") {
      this.selfSigned = true;
      console.log("Usao!"); 
    } else {
      this.selfSigned = false;
    }
  }
}