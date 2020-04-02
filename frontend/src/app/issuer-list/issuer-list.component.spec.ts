import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IssuerListComponent } from './issuer-list.component';

describe('IssuerListComponent', () => {
  let component: IssuerListComponent;
  let fixture: ComponentFixture<IssuerListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IssuerListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IssuerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
