import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProtectFormComponent } from './protect-form.component';

describe('ProtectFormComponent', () => {
  let component: ProtectFormComponent;
  let fixture: ComponentFixture<ProtectFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProtectFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProtectFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
