import { Component } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private http: HttpClient){}


  testEndPoint(){
    const testBody = {
      message:"Hello backend!"
    };
    this.http.post<any>(`${environment.link}/user/test`, testBody).subscribe(
      response => {
        // console.log('RESPONSE ', response);
      }
    );
  }
}
