import { Component, OnInit } from '@angular/core';
import { NewCertificate} from '../model/new-certificate';
import { CreateNewCertificateService} from '../services/create-new-certificate.service';
@Component({
  selector: 'app-create-new-certificate',
  templateUrl: './create-new-certificate.component.html',
  styleUrls: ['./create-new-certificate.component.css']
})
export class CreateNewCertificateComponent implements OnInit {

  newCertificate : NewCertificate;
  private selfSigned;

  constructor(private createNewCertificateService: CreateNewCertificateService) { }

  ngOnInit() {
  }

  onSumbitCreateNew(newCertificateForm) {
    this.newCertificate = newCertificateForm.value;
    console.log(this.newCertificate);
    this.createNewCertificateService.createNewCertificate(this.newCertificate);
  }
}
