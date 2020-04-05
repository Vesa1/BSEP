import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ShowAllCertificatesComponent } from './show-all-certificates/show-all-certificates.component';
import { CreateNewCertificateComponent } from './create-new-certificate/create-new-certificate.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';


const routes: Routes = [
    { path: 'showAllCertificates', component: ShowAllCertificatesComponent},
    { path: 'createNewCertificate', component: CreateNewCertificateComponent},
    { path: 'login', component: LoginComponent},
    { path: '', pathMatch: 'full',  component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [ShowAllCertificatesComponent, CreateNewCertificateComponent, LoginComponent];
