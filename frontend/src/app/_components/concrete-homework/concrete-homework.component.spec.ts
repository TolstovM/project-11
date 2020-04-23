import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConcreteHomeworkComponent } from './concrete-homework.component';

describe('ConcreteHomeworkComponent', () => {
  let component: ConcreteHomeworkComponent;
  let fixture: ComponentFixture<ConcreteHomeworkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConcreteHomeworkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConcreteHomeworkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
