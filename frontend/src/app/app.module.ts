import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule, routingComponents } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { IssuerListComponent } from './issuer-list/issuer-list.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './home/home.component';
import { MatTableModule } from "@angular/material/table";

@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    IssuerListComponent,
    HomeComponent
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule, BrowserAnimationsModule, MatTableModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
