import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowAllCertificatesComponent } from './show-all-certificates.component';

describe('ShowAllCertificatesComponent', () => {
  let component: ShowAllCertificatesComponent;
  let fixture: ComponentFixture<ShowAllCertificatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowAllCertificatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowAllCertificatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
