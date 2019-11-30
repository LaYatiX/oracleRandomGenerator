import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMieszkanie } from 'app/shared/model/mieszkanie.model';

@Component({
  selector: 'jhi-mieszkanie-detail',
  templateUrl: './mieszkanie-detail.component.html'
})
export class MieszkanieDetailComponent implements OnInit {
  mieszkanie: IMieszkanie;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ mieszkanie }) => {
      this.mieszkanie = mieszkanie;
    });
  }

  previousState() {
    window.history.back();
  }
}
