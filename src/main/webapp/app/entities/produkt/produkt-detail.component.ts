import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProdukt } from 'app/shared/model/produkt.model';

@Component({
  selector: 'jhi-produkt-detail',
  templateUrl: './produkt-detail.component.html'
})
export class ProduktDetailComponent implements OnInit {
  produkt: IProdukt;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ produkt }) => {
      this.produkt = produkt;
    });
  }

  previousState() {
    window.history.back();
  }
}
