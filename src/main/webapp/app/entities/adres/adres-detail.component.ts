import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdres } from 'app/shared/model/adres.model';

@Component({
  selector: 'jhi-adres-detail',
  templateUrl: './adres-detail.component.html'
})
export class AdresDetailComponent implements OnInit {
  adres: IAdres;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ adres }) => {
      this.adres = adres;
    });
  }

  previousState() {
    window.history.back();
  }
}
