import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LessonAddFormComponent } from './lesson-add-form.component';

describe('LessonAddFormComponent', () => {
  let component: LessonAddFormComponent;
  let fixture: ComponentFixture<LessonAddFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LessonAddFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LessonAddFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
