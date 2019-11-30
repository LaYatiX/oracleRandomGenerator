import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INieruchomosc } from 'app/shared/model/nieruchomosc.model';

@Component({
  selector: 'jhi-nieruchomosc-detail',
  templateUrl: './nieruchomosc-detail.component.html'
})
export class NieruchomoscDetailComponent implements OnInit {
  nieruchomosc: INieruchomosc;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nieruchomosc }) => {
      this.nieruchomosc = nieruchomosc;
    });
  }

  previousState() {
    window.history.back();
  }
}
