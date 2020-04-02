import { Component, OnInit } from '@angular/core';
import { IssuerListService} from '../services/issuer-list.service';

@Component({
  selector: 'app-issuer-list',
  templateUrl: './issuer-list.component.html',
  styleUrls: ['./issuer-list.component.css']
})
export class IssuerListComponent implements OnInit {

  constructor(private issuerListService: IssuerListService) { }

  ngOnInit() {
    this.issuerListService.issuerList();
  }

}
