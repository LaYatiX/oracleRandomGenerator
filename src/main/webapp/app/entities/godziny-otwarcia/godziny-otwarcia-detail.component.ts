import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGodzinyOtwarcia } from 'app/shared/model/godziny-otwarcia.model';

@Component({
  selector: 'jhi-godziny-otwarcia-detail',
  templateUrl: './godziny-otwarcia-detail.component.html'
})
export class GodzinyOtwarciaDetailComponent implements OnInit {
  godzinyOtwarcia: IGodzinyOtwarcia;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ godzinyOtwarcia }) => {
      this.godzinyOtwarcia = godzinyOtwarcia;
    });
  }

  previousState() {
    window.history.back();
  }
}
