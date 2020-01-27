import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-my-post',
  template: '<jhi-post onlyLoggedUser="true"></jhi-post>'
})
export class MyPostComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
