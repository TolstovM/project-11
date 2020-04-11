import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InstructorsSearchComponent } from './instructors-search.component';

describe('InstructorsSearchComponent', () => {
  let component: InstructorsSearchComponent;
  let fixture: ComponentFixture<InstructorsSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InstructorsSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InstructorsSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
