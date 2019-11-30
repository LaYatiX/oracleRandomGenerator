import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISklep } from 'app/shared/model/sklep.model';

@Component({
  selector: 'jhi-sklep-detail',
  templateUrl: './sklep-detail.component.html'
})
export class SklepDetailComponent implements OnInit {
  sklep: ISklep;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sklep }) => {
      this.sklep = sklep;
    });
  }

  previousState() {
    window.history.back();
  }
}
