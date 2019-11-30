import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IGodzinyOtwarcia } from 'app/shared/model/godziny-otwarcia.model';
import { GodzinyOtwarciaService } from './godziny-otwarcia.service';

@Component({
  selector: 'jhi-godziny-otwarcia',
  templateUrl: './godziny-otwarcia.component.html'
})
export class GodzinyOtwarciaComponent implements OnInit, OnDestroy {
  godzinyOtwarcias: IGodzinyOtwarcia[];
  eventSubscriber: Subscription;

  constructor(protected godzinyOtwarciaService: GodzinyOtwarciaService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.godzinyOtwarciaService.query().subscribe((res: HttpResponse<IGodzinyOtwarcia[]>) => {
      this.godzinyOtwarcias = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInGodzinyOtwarcias();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGodzinyOtwarcia) {
    return item.id;
  }

  registerChangeInGodzinyOtwarcias() {
    this.eventSubscriber = this.eventManager.subscribe('godzinyOtwarciaListModification', () => this.loadAll());
  }
}
