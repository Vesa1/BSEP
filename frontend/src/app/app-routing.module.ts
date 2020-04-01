import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ShowAllCertificatesComponent } from './show-all-certificates/show-all-certificates.component';
import { CreateNewCertificateComponent } from './create-new-certificate/create-new-certificate.component';


const routes: Routes = [
    { path: 'showAllCertificates', component: ShowAllCertificatesComponent},
    { path: 'createNewCertificate', component: CreateNewCertificateComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [ShowAllCertificatesComponent, CreateNewCertificateComponent];
