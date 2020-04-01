import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewCertificateComponent } from './create-new-certificate.component';

describe('CreateNewCertificateComponent', () => {
  let component: CreateNewCertificateComponent;
  let fixture: ComponentFixture<CreateNewCertificateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewCertificateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewCertificateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
