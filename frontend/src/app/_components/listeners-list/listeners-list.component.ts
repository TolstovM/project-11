import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'listeners-list',
  templateUrl: './listeners-list.component.html',
  styleUrls: ['./listeners-list.component.css']
})
export class ListenersListComponent implements OnInit {

  @Input() listeners;
  constructor() { }

  ngOnInit(): void {
  }

}
