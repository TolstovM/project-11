import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyLessonComponent } from './my-lesson.component';

describe('MyLessonComponent', () => {
  let component: MyLessonComponent;
  let fixture: ComponentFixture<MyLessonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyLessonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyLessonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
