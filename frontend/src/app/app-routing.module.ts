import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ShowAllCertificatesComponent } from './show-all-certificates/show-all-certificates.component';
import { CreateNewCertificateComponent } from './create-new-certificate/create-new-certificate.component';
import { LoginComponent } from './login/login.component';


const routes: Routes = [
    { path: 'showAllCertificates', component: ShowAllCertificatesComponent},
    { path: 'createNewCertificate', component: CreateNewCertificateComponent},
    { path: '', pathMatch: 'full',  component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [ShowAllCertificatesComponent, CreateNewCertificateComponent, LoginComponent];
