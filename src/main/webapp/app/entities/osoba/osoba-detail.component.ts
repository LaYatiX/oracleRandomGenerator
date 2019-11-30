import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOsoba } from 'app/shared/model/osoba.model';

@Component({
  selector: 'jhi-osoba-detail',
  templateUrl: './osoba-detail.component.html'
})
export class OsobaDetailComponent implements OnInit {
  osoba: IOsoba;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ osoba }) => {
      this.osoba = osoba;
    });
  }

  previousState() {
    window.history.back();
  }
}
