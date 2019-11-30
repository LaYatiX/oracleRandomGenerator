import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILokalizacja } from 'app/shared/model/lokalizacja.model';

@Component({
  selector: 'jhi-lokalizacja-detail',
  templateUrl: './lokalizacja-detail.component.html'
})
export class LokalizacjaDetailComponent implements OnInit {
  lokalizacja: ILokalizacja;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lokalizacja }) => {
      this.lokalizacja = lokalizacja;
    });
  }

  previousState() {
    window.history.back();
  }
}
