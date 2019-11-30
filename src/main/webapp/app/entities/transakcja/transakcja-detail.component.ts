import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransakcja } from 'app/shared/model/transakcja.model';

@Component({
  selector: 'jhi-transakcja-detail',
  templateUrl: './transakcja-detail.component.html'
})
export class TransakcjaDetailComponent implements OnInit {
  transakcja: ITransakcja;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transakcja }) => {
      this.transakcja = transakcja;
    });
  }

  previousState() {
    window.history.back();
  }
}
