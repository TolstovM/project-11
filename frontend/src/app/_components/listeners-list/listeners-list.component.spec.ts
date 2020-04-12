import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListenersListComponent } from './listeners-list.component';

describe('ListenersListComponent', () => {
  let component: ListenersListComponent;
  let fixture: ComponentFixture<ListenersListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListenersListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListenersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
